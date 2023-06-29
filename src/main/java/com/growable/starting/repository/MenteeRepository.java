package com.growable.starting.repository;

import com.growable.starting.model.Mentee;
import com.growable.starting.model.User;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MenteeRepository extends JpaRepository<Mentee, Long> {
    Optional<Mentee> findByEmail(String email);


    Mentee findByUser(User authenticatedUser);
}
