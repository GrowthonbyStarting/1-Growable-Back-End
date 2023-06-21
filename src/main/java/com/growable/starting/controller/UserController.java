package com.growable.starting.controller;

import com.growable.starting.dto.MentorDto;
import com.growable.starting.exception.StorageException;
import com.growable.starting.model.Mentee;
import com.growable.starting.model.Mentor;
import com.growable.starting.service.MenteeServiceImpl;
import com.growable.starting.service.MentorServiceImpl;
import com.growable.starting.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController {

    private final UserServiceImpl userService;
    private final MentorServiceImpl mentorService;
    private final MenteeServiceImpl menteeService;


    @Autowired
    public UserController(UserServiceImpl userService, MentorServiceImpl mentorService, MenteeServiceImpl menteeService) {
        this.userService = userService;
        this.mentorService = mentorService;
        this.menteeService = menteeService;
    }


    @PostMapping("/users/{userId}/become-mentor")
    public ResponseEntity<?> becomeMentor(@PathVariable Long userId, @RequestBody Mentor mentorDetails, MentorDto mentorDto) {
        mentorService.createMentor(mentorDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{userId}/become-mentee")
    public ResponseEntity<?> becomeMentee(@PathVariable Long userId, @RequestBody Mentee menteeDetails) {
        menteeService.becomeMentee(userId, menteeDetails);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mentor/profile-image")
    public ResponseEntity<?> uploadMentorProfileImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("mentorId") String mentorId) throws StorageException {
        String imageUrl = mentorService.storeMentorProfileImage(mentorId, image);
        return ResponseEntity.ok(imageUrl);
    }

    @PostMapping("/mentee/profile-image")
    public ResponseEntity<?> uploadMenteeProfileImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("menteeId") String menteeId) throws StorageException {
        String imageUrl = menteeService.storeMenteeProfileImage(menteeId, image);
        return ResponseEntity.ok(imageUrl);
    }
}
