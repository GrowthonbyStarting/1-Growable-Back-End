package com.growable.starting.controller;

import com.growable.starting.model.Review;
import com.growable.starting.service.ReviewServiceImpl;
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

    @PostMapping("/reviews")
    public ResponseEntity<?> createReview(@RequestBody Review review,
                                          Principal principal) {
        Review savedReview = reviewService.createReview(review, principal);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @GetMapping("/lectures/{lecture_id}/reviews")
    public ResponseEntity<?> getReviewsForLecture(@PathVariable("lecture_id") Long lectureId) {
        List<Review> reviews = reviewService.getReviewsForLecture(lectureId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/mentors/{mentor_id}/reviews")
    public ResponseEntity<?> getReviewsForMentor(@PathVariable("mentor_id") Long mentorId) {
        List<Review> reviews = reviewService.getReviewsForMentor(mentorId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}

