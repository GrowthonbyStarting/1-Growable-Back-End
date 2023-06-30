package com.growable.starting.repository;

import com.growable.starting.model.Mentor;
import com.growable.starting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MentorRepository extends JpaRepository<Mentor, Long> , MentorRepositoryCustom {
}

