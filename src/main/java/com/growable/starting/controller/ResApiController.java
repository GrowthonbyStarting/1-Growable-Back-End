package com.growable.starting.controller;

import com.growable.starting.model.Lecture;
import com.growable.starting.model.Mentor;
import com.growable.starting.repository.LectureRepository;
import com.growable.starting.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ResApiController {

    private final LectureRepository lectureRepository;
    private final MentorRepository mentorRepository;

    @Autowired
    public ResApiController(LectureRepository lectureRepository, MentorRepository mentorRepository) {
        this.lectureRepository = lectureRepository;
        this.mentorRepository = mentorRepository;
    }

    @GetMapping("/send-mentorLecture")
    public ResponseEntity<List<Lecture>> sendLecture() {
        List<Lecture> lectures = lectureRepository.findAll();

        for (Lecture lecture : lectures) {
            if (lecture.getMentor() != null) {
                Long mentorId = lecture.getMentor().getMentorId();

                Optional<Mentor> mentorWithCompanyInfos = mentorRepository.findMentorWithCompanyInfos(mentorId);
                mentorWithCompanyInfos.ifPresent(mentor -> lecture.getMentor().setCompanyInfos(mentor.getCompanyInfos()));

                Optional<Mentor> mentorWithLectureExperiences = mentorRepository.findMentorWithLectureExperiences(mentorId);
                mentorWithLectureExperiences.ifPresent(mentor -> lecture.getMentor().setLectureExperiences(mentor.getLectureExperiences()));
            }
        }

        return ResponseEntity.ok().body(lectures);
    }
}
