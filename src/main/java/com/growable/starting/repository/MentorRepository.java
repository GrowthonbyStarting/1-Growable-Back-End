package com.growable.starting.repository;

import com.growable.starting.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MentorRepository extends JpaRepository<Mentor, Long> {

    @Query("select avg(r.starScore) FROM Review r WHERE r.mentor.mentorId = :mentorId")
    double avgStarScore(@Param("mentorId") Long mentorId);
}
