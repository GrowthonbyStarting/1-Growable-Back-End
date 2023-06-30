package com.growable.starting.repository;

import com.growable.starting.model.User;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findByKakaoId(Long kakaoId);
}
