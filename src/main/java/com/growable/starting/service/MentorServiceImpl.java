package com.growable.starting.service;

import com.growable.starting.dto.Identity;
import com.growable.starting.dto.MentorDto;
import com.growable.starting.exception.StorageException;
import com.growable.starting.model.Company;
import com.growable.starting.model.LectureExperience;
import com.growable.starting.model.Mentor;
import com.growable.starting.model.User;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MentorServiceImpl implements MentorService{
    private final Path mentorProfileImageDir = Paths.get("uploads/profile-images/mentor");
    private final UserRepository userRepository;

    private final MentorRepository mentorRepository;

    @Autowired
    public MentorServiceImpl(UserRepository userRepository, MentorRepository mentorRepository) {
        this.userRepository = userRepository;
        this.mentorRepository = mentorRepository;
    }

    @Override
    @Transactional
    public String storeMentorProfileImage(String mentorId, MultipartFile image) throws StorageException {
        if (image.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        try {
            // 연습용으로 로컬 저장소용 폴더를 생성하였습니다.
            Path mentorProfileImageDir = Paths.get("mentorProfileImages");
            Files.createDirectories(mentorProfileImageDir);

            // 개별 멘토의 프로필 이미지를 저장할 경로를 만듭니다.
            Path mentorImageDir = mentorProfileImageDir.resolve(mentorId);
            Files.createDirectories(mentorImageDir);

            // 이미지 파일 이름 생성
            String uniqueImageName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(image.getOriginalFilename());

            // 이미지를 지정된 경로로 복사
            Path destination = mentorImageDir.resolve(uniqueImageName);
            Files.copy(image.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            Mentor mentor = mentorRepository.findById(Long.valueOf(mentorId)).orElseThrow(() -> new IllegalStateException("Cannot find mentor with id " + mentorId));
            mentor.setProfileImageUrl(destination.toString());
            mentorRepository.save(mentor);

            return destination.toString(); // 이미지의 경로를 반환합니다.
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Transactional
    @Override
    public void createMentor(MentorDto mentorDto) {
        User user = userRepository.findById(mentorDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id: " + mentorDto.getUserId()));

        Mentor mentor = new Mentor();
        mentor.setName(user.getKakaoNickname());
        mentor.setEmail(user.getKakaoEmail());
        mentor.setIdentity(Identity.MENTOR);
        mentor.setPoint(mentorDto.getPoint());
        mentor.setChatUrl(mentorDto.getChatUrl());
        mentor.setCategory(mentorDto.getCategory());
        mentor.setSubcategory(mentorDto.getSubcategory());
        mentor.setKeywords(mentorDto.getKeywords());

        List<Company> companyInfos = mentorDto.getCompanyInfos().stream()
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
    }


}