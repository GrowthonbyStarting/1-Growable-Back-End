package com.growable.starting.service;

import com.growable.starting.dto.MentorDto;
import com.growable.starting.exception.StorageException;
import com.growable.starting.model.Mentee;
import com.growable.starting.model.Mentor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MentorService {

    Mentor storeMentorProfileImage(String mentorId, MultipartFile image) throws StorageException; //사진 업로드

    Mentor createMentor(MentorDto mentorDto); //일반 유저 -> 멘토(추가정보 입력)

    List<Mentee> getMenteesForLecture(Long lectureId, Long mentorId);
}
