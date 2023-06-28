package com.growable.starting.controller;

import com.growable.starting.dto.ReviewDto;
import com.growable.starting.model.CreateReviewResponse;
import com.growable.starting.model.Mentor;
import com.growable.starting.model.Reply;
import com.growable.starting.model.Review;
import com.growable.starting.service.ReviewServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    @Autowired
    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    @ApiOperation("리뷰 작성")
    @PostMapping("/create/{mentee_id}/{lecture_id}")
    public ResponseEntity<?> createReview(@RequestBody ReviewDto reviewDto,
                                          @PathVariable("lecture_id") Long lectureId, @PathVariable("mentee_id") Long menteeId) {
        Pair<Review, Mentor> result = reviewService.createReview(reviewDto, lectureId, menteeId);
        Review savedReview = result.getFirst();
        Mentor updatedMentor = result.getSecond();

        return new ResponseEntity<>(new CreateReviewResponse(savedReview, updatedMentor), HttpStatus.CREATED);
    }

    @ApiOperation("리뷰삭제")
    @DeleteMapping("/delete/{review_Id}")
    public ResponseEntity<?> deleteReview(@PathVariable("review_Id") Long reviewId) {
        Review review = reviewService.deleteReviews(reviewId);
        return ResponseEntity.ok(review);
    }

    @ApiOperation("멘토 리뷰확인")
    @GetMapping("/mentors/{mentor_id}/reviews")
    public ResponseEntity<?> getReviewsForMentor(@PathVariable("mentor_id") Long mentorId) {
        List<Review> reviews = reviewService.getReviewsForMentor(mentorId);
        return ResponseEntity.ok(reviews);
    }

    // 댓글 작성 기능
    @PostMapping("/reply/{review_Id}/{mentor_Id}")
    public ResponseEntity<Reply> addReply(String content, @PathVariable("review_Id") Long reviewId, @PathVariable("mentor_Id") Long mentorId) {
        Reply replyEntity = reviewService.addReply(reviewId, mentorId, content);
        return ResponseEntity.ok(replyEntity);
    }
}

