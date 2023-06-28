package com.growable.starting.service;

import com.growable.starting.dto.LectureDto;
import com.growable.starting.model.Enrollment;
import com.growable.starting.model.Lecture;

import java.util.List;

public interface LectureService {

    Lecture createLecture(Long mentorId, LectureDto lectureDto);

    Enrollment enrollInLecture(Long menteeId, Long lectureId);

    Enrollment cancelLectureEnrollment(Long menteeId, Long lectureId);

    List<Lecture> findAllLectures();
}
