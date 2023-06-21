package com.growable.starting.controller;

import com.growable.starting.exception.NotFoundException;
import com.growable.starting.exception.StorageException;
import com.growable.starting.model.Mentee;
import com.growable.starting.service.LectureServiceImpl;
import com.growable.starting.service.MenteeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MenteeController {

    private final MenteeServiceImpl menteeService;
    private final LectureServiceImpl lectureService;

    @Autowired
    public MenteeController(MenteeServiceImpl menteeService, LectureServiceImpl lectureService) {
        this.menteeService = menteeService;
        this.lectureService = lectureService;
    }

    @PostMapping("/{lectureId}/enroll/{menteeId}")
    public ResponseEntity<?> enrollInLecture(@PathVariable Long lectureId, @PathVariable Long menteeId) {
        try {
            lectureService.enrollInLecture(menteeId, lectureId);
            return new ResponseEntity<>("Enrollment successful", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{lectureId}/cancel/{menteeId}")
    public ResponseEntity<?> cancelLectureEnrollment(@PathVariable Long lectureId, @PathVariable Long menteeId) {
        try {
            lectureService.cancelLectureEnrollment(menteeId, lectureId);
            return new ResponseEntity<>("Enrollment cancellation successful", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/users/{userId}/become-mentee")
    public ResponseEntity<?> becomeMentee(@PathVariable Long userId, @RequestBody Mentee menteeDetails) {
        try {
            menteeService.becomeMentee(userId, menteeDetails);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/mentee/profile-image")
    public ResponseEntity<?> uploadMenteeProfileImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("menteeId") String menteeId) throws StorageException {
        String imageUrl = menteeService.storeMenteeProfileImage(menteeId, image);
        return ResponseEntity.ok(imageUrl);
    }
}
