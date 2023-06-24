package com.growable.starting.service;

import com.growable.starting.dto.LectureDto;
import com.growable.starting.model.Enrollment;
import com.growable.starting.model.Lecture;

public interface LectureService {

    Lecture createLecture(Long mentorId, LectureDto lectureDto);

    void enrollInLecture(Long menteeId, Long lectureId);

    Enrollment cancelLectureEnrollment(Long menteeId, Long lectureId);
}
