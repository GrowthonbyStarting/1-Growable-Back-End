package com.growable.starting.service;

import com.growable.starting.dto.MenteeDto;
import com.growable.starting.exception.MenteeNotFoundException;
import com.growable.starting.exception.StorageException;
import com.growable.starting.model.Mentee;
import com.growable.starting.model.User;
import com.growable.starting.model.type.Identity;
import com.growable.starting.repository.MenteeRepository;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class MenteeServiceImpl implements MenteeService {
    private final Path menteeProfileImageDir = Paths.get("uploads/profile-images/mentee");

    private final UserRepository userRepository;
    private final MenteeRepository menteeRepository;

    @Autowired
    public MenteeServiceImpl(UserRepository userRepository, MenteeRepository menteeRepository) {
        this.userRepository = userRepository;
        this.menteeRepository = menteeRepository;
    }

    @Override
    @Transactional
    public Mentee becomeMentee(Long userCode, MenteeDto menteeDto) {
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id: " + userCode));

        if (user.getMentee() != null) {
            throw new IllegalStateException("이미 멘티로 등록된 사용자입니다.");
        }

        Mentee mentee = new Mentee();
        mentee.setName(menteeDto.getName());
        mentee.setEmail(menteeDto.getEmail());
        mentee.setIdentity(Identity.MENTEE);
        mentee.setStartingUrl(menteeDto.getStartingUrl());
        mentee.setImageUrl(user.getKakaoProfileImg());
        mentee.setUser(user);
        mentee.setPoint(menteeDto.getPoint());
        mentee.setPhoneNumber(menteeDto.getPhoneNumber());
        mentee.setBankName(mentee.getBankName());
        mentee.setAccount(mentee.getAccount());
        mentee.setPoint(0);
        user.setMentee(mentee);
        menteeRepository.save(mentee);
        userRepository.save(user);
        return menteeRepository.save(mentee);
    }

    @Override
    @Transactional
    public Mentee storeMenteeProfileImage(Long menteeId, MultipartFile image) throws StorageException {
        if (image.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        try {
            Path menteeProfileImageDir = Paths.get("menteeProfileImages");
            Files.createDirectories(menteeProfileImageDir);

            String uniqueImageName = menteeId + "_" + UUID.randomUUID() + "." + FilenameUtils.getExtension(image.getOriginalFilename());
            Path destination = menteeProfileImageDir.resolve(uniqueImageName);
            Files.copy(image.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            Optional<Mentee> optionalMentee = menteeRepository.findById(menteeId);
            if (optionalMentee.isEmpty()) {
                throw new MenteeNotFoundException("Cannot find mentee with id " + menteeId);
            }
            Mentee mentee = optionalMentee.get();
            String imageUrl = "/menteeProfileImages/" + uniqueImageName;
            mentee.setImageUrl(imageUrl);
            menteeRepository.save(mentee);

            return mentee;
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }
}

