package com.growable.starting.repository;

import com.growable.starting.model.Mentor;
import com.growable.starting.model.User;

import java.util.Optional;

public interface MentorRepositoryCustom {
    double avgStarScore(Long mentorId);
    Mentor findByUser(User authenticatedUser);
    Optional<Mentor> findMentorWithCompanyInfos(Long mentorId);
    Optional<Mentor> findMentorWithLectureExperiences(Long mentorId);
}
