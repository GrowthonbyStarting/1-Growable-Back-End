package com.growable.starting.repository;

import com.growable.starting.model.Mentee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeRepository extends JpaRepository<Mentee, Long> {
}
