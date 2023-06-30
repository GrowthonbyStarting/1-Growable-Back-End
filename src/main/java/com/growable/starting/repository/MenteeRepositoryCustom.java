package com.growable.starting.repository;

import com.growable.starting.model.Mentee;
import com.growable.starting.model.User;

import java.util.Optional;

public interface MenteeRepositoryCustom {
    Optional<Mentee> findByEmail(String email);
    Mentee findByUser(User authenticatedUser);
}
