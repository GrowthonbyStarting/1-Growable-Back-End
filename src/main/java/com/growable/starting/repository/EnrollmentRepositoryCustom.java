package com.growable.starting.repository;

import com.growable.starting.model.Enrollment;
import com.growable.starting.model.Lecture;
import com.growable.starting.model.Mentee;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepositoryCustom {
    Optional<Enrollment> findByMenteeAndLecture(Mentee mentee, Lecture lecture);

    Optional<Enrollment> findByLectureIdAndMenteeId(Long lectureId, Long menteeId);

    List<Enrollment> findByLectureIdAndMentorId(Long lectureId, Long mentorId);
}
