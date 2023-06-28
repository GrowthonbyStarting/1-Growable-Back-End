package com.growable.starting.controller;

import com.growable.starting.model.Lecture;
import com.growable.starting.service.LectureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lecture")
public class LectureController {

    private final LectureServiceImpl lectureService;

    @Autowired
    public LectureController(LectureServiceImpl lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/search-all")
    public ResponseEntity<List<Lecture>> sendLectureAll(){
        List<Lecture> allLecture = lectureService.findAllLectures();
        return ResponseEntity.ok(allLecture);
    }
}
