package com.growable.starting.controller;

import com.growable.starting.model.Lecture;
import com.growable.starting.service.LectureService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    //  사용자가 서비스에 접근할 때 마다 react -> spring 으로 요청을 보내 최신 강의,멘토 정보 update
    @ApiOperation("챌린지의 관한 정보 전달")
    @GetMapping("/lectures")
    public ResponseEntity<List<Lecture>> getAllLectures() {
        List<Lecture> lectures = lectureService.findAllLectures();
        return new ResponseEntity<>(lectures, HttpStatus.OK);
    }
}
