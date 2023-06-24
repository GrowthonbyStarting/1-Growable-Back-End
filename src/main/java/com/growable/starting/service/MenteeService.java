package com.growable.starting.service;

import com.growable.starting.dto.MenteeDto;
import com.growable.starting.exception.StorageException;
import com.growable.starting.model.Mentee;
import org.springframework.web.multipart.MultipartFile;

public interface MenteeService {


    Mentee becomeMentee(Long userId, MenteeDto menteeDto);

    Mentee storeMenteeProfileImage(String menteeId, MultipartFile image) throws StorageException;

}

