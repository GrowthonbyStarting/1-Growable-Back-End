package com.growable.starting.repository;

import com.growable.starting.model.Mentee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenteeRepository extends JpaRepository<Mentee, Long> {
    @Query("SELECT m FROM Mentee m WHERE m.menteeId = :menteeId")
    Mentee findById(@Param("menteeId") long menteeId);
}
