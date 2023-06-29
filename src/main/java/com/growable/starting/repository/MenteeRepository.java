package com.growable.starting.repository;

import com.growable.starting.model.Mentee;
import com.growable.starting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenteeRepository extends JpaRepository<Mentee, Long> {
    Optional<Mentee> findByEmail(String email);


    Mentee findByUser(User authenticatedUser);
}
