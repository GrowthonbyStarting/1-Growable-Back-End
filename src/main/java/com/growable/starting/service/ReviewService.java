package com.growable.starting.service;

import com.growable.starting.dto.ReviewDto;
import com.growable.starting.model.*;
import org.springframework.data.util.Pair;

import java.security.Principal;
import java.util.List;

public interface ReviewService {

    List<Review> getReviewsForMentor(Long mentorId);

    boolean isLectureEndDateAfterNow(Lecture lecture);


    Review deleteReviews(Long reviewId);

    Reply addReply(Long reviewId, Long mentorId, String content);

    Pair<Review, Mentor> createReview(ReviewDto reviewDto, Long lectureId, Long menteeId);

}
