package com.growable.starting.service;

import com.growable.starting.dto.LectureDto;
import com.growable.starting.model.Lecture;
import org.springframework.transaction.annotation.Transactional;

public interface LectureService {

    Lecture createLecture(Long mentorId, LectureDto lectureDto);

    void enrollInLecture(Long menteeId, Long lectureId);

    void cancelLectureEnrollment(Long menteeId, Long lectureId);
}
