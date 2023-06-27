package com.growable.starting.repository;

import com.growable.starting.model.Mentee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MenteeRepository extends JpaRepository<Mentee, Long> {
    @Query("SELECT m FROM Mentee m WHERE m.menteeId = :menteeId")
    Mentee findById(@Param("menteeId") long menteeId);

    Optional<Mentee> findByEmail(String email);

    @Query("SELECT m.menteeId FROM Mentee m WHERE m.email = :email")
    Long findMenteeIdByEmail(@Param("email") String email);
}
