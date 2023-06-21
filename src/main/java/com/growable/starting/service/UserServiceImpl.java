package com.growable.starting.service;

import com.growable.starting.dto.Identity;
import com.growable.starting.model.Mentee;
import com.growable.starting.model.Mentor;
import com.growable.starting.model.User;
import com.growable.starting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
