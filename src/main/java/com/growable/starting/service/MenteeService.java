package com.growable.starting.service;

import com.growable.starting.exception.StorageException;
import com.growable.starting.model.Mentee;
import com.growable.starting.model.Mentor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface MenteeService {


    void becomeMentee(Long userId, Mentee menteeDetails);

    String storeMenteeProfileImage(String menteeId, MultipartFile image) throws StorageException;

}

