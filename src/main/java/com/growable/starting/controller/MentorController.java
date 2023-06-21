package com.growable.starting.controller;

import com.growable.starting.dto.LectureDto;
import com.growable.starting.dto.MentorDto;
import com.growable.starting.exception.StorageException;
import com.growable.starting.model.Lecture;
import com.growable.starting.service.LectureServiceImpl;
import com.growable.starting.service.MentorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MentorController {

    private final MentorServiceImpl mentorService;
    private final LectureServiceImpl lectureService;

    @Autowired
    public MentorController(MentorServiceImpl mentorService,LectureServiceImpl lectureService) {
        this.mentorService = mentorService;
        this.lectureService = lectureService;
    }


    @PostMapping("/users/{userId}/become-mentor")
    public ResponseEntity<?> becomeMentor(@PathVariable Long userId, @RequestBody MentorDto mentorDto) {
        mentorService.createMentor(mentorDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mentor/profile-image")
    public ResponseEntity<?> uploadMentorProfileImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("mentorId") String mentorId) throws StorageException {
        String imageUrl = mentorService.storeMentorProfileImage(mentorId, image);
        return ResponseEntity.ok(imageUrl);
    }

    @PostMapping("/create/lectures/{mentorId}")
    public ResponseEntity<Lecture> createLecture(@PathVariable Long mentorId, @RequestBody LectureDto lectureDto) {
        Lecture lecture = lectureService.createLecture(mentorId, lectureDto);
        return new ResponseEntity<>(lecture, HttpStatus.CREATED);
    }
}

