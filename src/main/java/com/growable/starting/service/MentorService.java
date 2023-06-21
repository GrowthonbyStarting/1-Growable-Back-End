package com.growable.starting.service;

import com.growable.starting.dto.MentorDto;
import com.growable.starting.exception.StorageException;
import org.springframework.web.multipart.MultipartFile;

public interface MentorService {

    String storeMentorProfileImage(String mentorId,MultipartFile image) throws StorageException; //사진 업로드
    void createMentor(MentorDto mentorDto); //일반 유저 -> 멘토(추가정보 입력)
}