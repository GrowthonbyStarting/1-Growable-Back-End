package com.growable.starting.service;

import com.growable.starting.dto.MentorDto;
import com.growable.starting.exception.MentorNotFoundException;
import com.growable.starting.exception.StorageException;
import com.growable.starting.model.*;
import com.growable.starting.model.type.Identity;
import com.growable.starting.repository.EnrollmentRepository;
import com.growable.starting.repository.MentorRepository;
import com.growable.starting.repository.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MentorServiceImpl implements MentorService {
    private final Path mentorProfileImageDir = Paths.get("uploads/profile-images/mentor");
    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public MentorServiceImpl(UserRepository userRepository, MentorRepository mentorRepository, EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.mentorRepository = mentorRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    @Transactional
    public Mentor storeMentorProfileImage(Long mentorId, MultipartFile image) throws StorageException {
        if (image.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        try {
            // 연습용으로 로컬 저장소용 폴더를 생성하였습니다.
            Path mentorProfileImageDir = Paths.get("mentorProfileImages");
            Files.createDirectories(mentorProfileImageDir);

            String uniqueImageName = mentorId + "_" + UUID.randomUUID()+ "." + FilenameUtils.getExtension(image.getOriginalFilename());
            Path destination = mentorProfileImageDir.resolve(uniqueImageName);
            Files.copy(image.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            Optional<Mentor> optionalMentor = mentorRepository.findById(mentorId);
            if(optionalMentor.isEmpty()){
                throw new MentorNotFoundException("Cannot find mentor with id" + mentorId);
            }
            Mentor mentor = optionalMentor.get();
            String imageUrl = "/mentorProfileImages/" + uniqueImageName;
            mentor.setProfileImageUrl(imageUrl);
            mentorRepository.save(mentor);
            return mentor;
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }


    @Transactional
    @Override
    public Mentor createMentor(MentorDto mentorDto) {
        User user = userRepository.findById(mentorDto.getUser().getUserCode())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id: " + mentorDto.getUser().getUserCode()));

        Mentor mentor = new Mentor();
        mentor.setName(mentorDto.getName());
        mentor.setEmail(mentorDto.getEmail());
        mentor.setIdentity(Identity.MENTOR);
        mentor.setChatUrl(mentorDto.getChatUrl());
        mentor.setCategory(mentorDto.getCategory());
        mentor.setSubcategory(mentorDto.getSubcategory());
        mentor.setKeywords(mentorDto.getKeywords());
        mentor.setBankName(mentorDto.getBankName());
        mentor.setAccount(mentorDto.getAccount());
        mentor.setPoint(0);

        List<Company> companyInfos = mentorDto.getCompany().stream()
                .map(infoDto -> {
                    Company info = new Company();
                    info.setCompanyName(infoDto.getCompanyName());
                    info.setWorkType(infoDto.getWorkType());
                    info.setPosition(infoDto.getPosition());
                    info.setStartDate(infoDto.getStartDate());
                    info.setEndDate(infoDto.getEndDate());
                    return info;
                }).collect(Collectors.toList());
        mentor.setCompanyInfos(companyInfos);

        List<LectureExperience> lectureExperiences = mentorDto.getLectureExperiences().stream()
                .map(expDto -> {
                    LectureExperience exp = new LectureExperience();
                    exp.setLectureTitle(expDto.getLectureTitle());
                    exp.setCompanyName(expDto.getCompanyName());
                    exp.setLectureType(expDto.getLectureType());
                    exp.setLectureField(expDto.getLectureField());
                    exp.setStartDate(expDto.getStartDate());
                    exp.setEndDate(expDto.getEndDate());
                    return exp;
                }).collect(Collectors.toList());
        mentor.setLectureExperiences(lectureExperiences);

        user.setMentor(mentor);
        userRepository.save(user);
        mentorRepository.save(mentor);
        return mentor;
    }

    @Transactional
    @Override
    public List<Mentee> getMenteesForLecture(Long lectureId, Long mentorId) {
        List<Enrollment> enrollments = enrollmentRepository.findByLectureIdAndMentorId(lectureId, mentorId);
        return enrollments.stream().map(Enrollment::getMentee).collect(Collectors.toList());
    }
}