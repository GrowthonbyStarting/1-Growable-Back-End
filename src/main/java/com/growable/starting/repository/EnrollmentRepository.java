package com.growable.starting.repository;

import com.growable.starting.model.Enrollment;
import com.growable.starting.model.Lecture;
import com.growable.starting.model.Mentee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    @Query("SELECT e FROM Enrollment e WHERE e.mentee = :mentee AND e.lecture = :lecture")
    Optional<Enrollment> findByMenteeAndLecture(Mentee mentee, Lecture lecture);
}
