package com.growable.starting.repository;

import com.growable.starting.model.Review;

import java.util.List;

public interface ReviewRepositoryCustom {
    List<Review> findReviewsByMentorId(Long mentorId);

    Review deleteReview(Long reviewId);
}
