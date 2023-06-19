package com.growable.starting.service;

import com.growable.starting.model.Mentee;
import com.growable.starting.model.Mentor;

public interface UserService {

    void becomeMentor(Long userId, Mentor mentorDetails);

    void becomeMentee(Long userId, Mentee menteeDetails);
}
