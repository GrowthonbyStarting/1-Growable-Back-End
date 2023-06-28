package com.growable.starting.controller;

import com.growable.starting.dto.LectureDto;
import com.growable.starting.dto.LectureWithStatusDto;
import com.growable.starting.dto.MentorDto;
import com.growable.starting.exception.StorageException;
import com.growable.starting.model.Lecture;
import com.growable.starting.model.Mentee;
import com.growable.starting.model.Mentor;
import com.growable.starting.repository.LectureRepository;
import com.growable.starting.service.LectureServiceImpl;
import com.growable.starting.service.MentorServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mentor")
public class MentorController {

    private final MentorServiceImpl mentorService;
    private final LectureServiceImpl lectureService;
    private final LectureRepository lectureRepository;

    @Autowired
    public MentorController(MentorServiceImpl mentorService, LectureServiceImpl lectureService, LectureRepository lectureRepository) {
        this.mentorService = mentorService;
        this.lectureService = lectureService;
        this.lectureRepository = lectureRepository;
    }

    @ApiOperation("유저에서 멘토로 전환")
    @PostMapping("/users/{userId}/become-mentor")
    public ResponseEntity<Mentor> becomeMentor(@PathVariable Long userId, @RequestBody MentorDto mentorDto) {
        Mentor mentor = mentorService.createMentor(mentorDto);
        return ResponseEntity.ok(mentor);
    }

    @ApiOperation("멘토 프로필 사진 업데이트")
    @PostMapping("/mentor/profile-image")
    public ResponseEntity<Mentor> uploadMentorProfileImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("mentorId") String mentorId) throws StorageException {
        Mentor updatedMentor = mentorService.storeMentorProfileImage(mentorId, image);
        return ResponseEntity.ok(updatedMentor);
    }

    @ApiOperation("챌린지 개설")
    @PostMapping("/create/lectures/{mentorId}")
    public ResponseEntity<Lecture> createLecture(@PathVariable Long mentorId, @RequestBody LectureDto lectureDto) {
        Lecture lecture = lectureService.createLecture(mentorId, lectureDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(lecture);
    }

    @ApiOperation("챌린지를 신청한 멘티 확인")
    @GetMapping("/lectures/{lectureId}/mentees/{mentorId}")
    public ResponseEntity<List<Mentee>> getMenteesForLecture(@PathVariable(name = "lectureId") Long lectureId,
                                                             @PathVariable(name = "mentorId") Long mentorId) {
        List<Mentee> menteeList = mentorService.getMenteesForLecture(lectureId, mentorId);
        return ResponseEntity.ok(menteeList);
    }

    @ApiOperation("30초마다 강의 모집상태 반환")
    @GetMapping("/lecturesWithStatus")
    public ResponseEntity<List<LectureWithStatusDto>> getAllLecturesWithStatus() {
        List<Lecture> lectures = lectureRepository.findAll();

        List<LectureWithStatusDto> lectureWithStatusDto = lectures.stream().map(lecture -> {
            LectureWithStatusDto dto = new LectureWithStatusDto();
            dto.setId(lecture.getId());
            dto.setLectureStatus(lecture.getStatus());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(lectureWithStatusDto);
    }
}

