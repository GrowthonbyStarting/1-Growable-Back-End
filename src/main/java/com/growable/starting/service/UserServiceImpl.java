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

    @Override
    @Transactional
    public void becomeMentor(Long userId, Mentor mentorDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id: " + userId));

        Mentor mentor = new Mentor();
        mentor.setName(user.getKakaoNickname());   // 이름을 사용자로부터 가져옴
        mentor.setEmail(user.getKakaoEmail());     // 이메일을 사용자로부터 가져옴
        mentor.setIdentity(Identity.MENTOR);
        mentor.setTuition(mentorDetails.getTuition());
        mentor.setChatUrl(mentorDetails.getChatUrl());
        mentor.setUser(user);

        user.setMentor(mentor);
        userRepository.save(user);
    }
    @Override
    @Transactional
    public void becomeMentee(Long userId, Mentee menteeDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id: " + userId));

        Mentee mentee = new Mentee();
        mentee.setName(user.getKakaoNickname());
        mentee.setEmail(user.getKakaoEmail());
        mentee.setIdentity(Identity.MENTEE);
        mentee.setStartingUrl(menteeDetails.getStartingUrl());
        mentee.setUser(user);

        user.setMentee(mentee);
        userRepository.save(user);
    }
}
