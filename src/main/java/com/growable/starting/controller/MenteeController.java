package com.growable.starting.controller;

import com.growable.starting.dto.MenteeDto;
import com.growable.starting.exception.NotFoundException;
import com.growable.starting.exception.StorageException;
import com.growable.starting.model.Enrollment;
import com.growable.starting.model.Mentee;
import com.growable.starting.service.LectureServiceImpl;
import com.growable.starting.service.MenteeService;
import com.growable.starting.service.MenteeServiceImpl;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("챌린지 신청") //수정 필요 06.25
    @PostMapping("/{lectureId}/enroll/{menteeId}")
    public ResponseEntity<?> enrollInLecture(@PathVariable Long lectureId, @PathVariable Long menteeId) {
        try {
            lectureService.enrollInLecture(menteeId, lectureId);
            return new ResponseEntity<>("Enrollment successful", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("챌친지 신청 취소")
    @DeleteMapping("/{lectureId}/cancel/{menteeId}")
    public ResponseEntity<?> cancelLectureEnrollment(@PathVariable Long lectureId, @PathVariable Long menteeId) {
        try {
            Enrollment enrollment = lectureService.cancelLectureEnrollment(menteeId, lectureId);
            return ResponseEntity.ok(enrollment);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ApiOperation("유저에서 멘티로 전환")
    @PostMapping("/users/{userId}/become-mentee")
    public ResponseEntity<?> becomeMentee(@PathVariable Long userId, @RequestBody MenteeDto menteeDto) {
        try {
            Mentee mentee = menteeService.becomeMentee(userId, menteeDto);
            return ResponseEntity.ok(mentee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ApiOperation("멘티 프로필 사진 업데이트")
    @PostMapping("/mentee/profile-image")
    public ResponseEntity<?> uploadMenteeProfileImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("menteeId") String menteeId) throws StorageException {
        Mentee mentee = menteeService.storeMenteeProfileImage(menteeId, image);
        return ResponseEntity.ok(mentee);
    }
}
