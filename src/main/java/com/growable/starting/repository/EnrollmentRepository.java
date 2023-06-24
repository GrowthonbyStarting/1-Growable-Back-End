package com.growable.starting.repository;

import com.growable.starting.model.Enrollment;
import com.growable.starting.model.Lecture;
import com.growable.starting.model.Mentee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    @Query("SELECT e FROM Enrollment e WHERE e.mentee = :mentee AND e.lecture = :lecture")
    Optional<Enrollment> findByMenteeAndLecture(@Param("mentee") Mentee mentee,@Param("lecture") Lecture lecture);


    @Query("SELECT e.lecture.id, e.mentee.menteeId FROM Enrollment e WHERE e.lecture.id = :lectureId AND e.mentee.menteeId = :menteeId")
    Optional<Enrollment> findByLectureIdAndMenteeId(@Param("lectureId") Long lectureId, @Param("menteeId") Long menteeId);

    @Query("SELECT e.mentee FROM Enrollment e WHERE e.lecture.id = :lectureId AND e.mentor.mentorId = :mentorId")
    List<Enrollment> findByLectureIdAndMentorId(@Param("lectureId")Long lectureId, @Param("mentorId")Long mentorId);
}
