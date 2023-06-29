package com.growable.starting.repository;

import com.growable.starting.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query("SELECT count(e.mentee) FROM Enrollment e WHERE e.lecture.id = :lectureId AND e.lecture.recruitmentEndDate > CURRENT_DATE")
    int countEnrolledStudentsForCurrentLecture(@Param("lectureId") Long lectureId);
}
