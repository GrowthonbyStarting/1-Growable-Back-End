package com.growable.starting.controller;

import com.growable.starting.model.Review;
import com.growable.starting.service.ReviewServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    @Autowired
    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    @ApiOperation("리뷰 목록")
    @PostMapping("/reviews")
    public ResponseEntity<?> createReview(@RequestBody Review review,
                                          Principal principal) {
        Review savedReview = reviewService.createReview(review, principal);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @ApiOperation("강의 리뷰확인")
    @GetMapping("/lectures/{lecture_id}/reviews")
    public ResponseEntity<?> getReviewsForLecture(@PathVariable("lecture_id") Long lectureId) {
        List<Review> reviews = reviewService.getReviewsForLecture(lectureId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @ApiOperation("멘토 리뷰확인")
    @GetMapping("/mentors/{mentor_id}/reviews")
    public ResponseEntity<?> getReviewsForMentor(@PathVariable("mentor_id") Long mentorId) {
        List<Review> reviews = reviewService.getReviewsForMentor(mentorId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}

