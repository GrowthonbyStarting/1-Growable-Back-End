package com.growable.starting.repository;

import com.growable.starting.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>, EnrollmentRepositoryCustom {
}
