package com.growable.starting.service;

import com.growable.starting.model.Lecture;
import com.growable.starting.model.Mentee;
import com.growable.starting.model.Review;

import java.security.Principal;
import java.util.List;

public interface ReviewService {

    Review createReview(Review review, Principal principal);

    List<Review> getReviewsForLecture(Long lectureId);

    List<Review> getReviewsForMentor(Long mentorId);

    boolean isLectureEndDateAfterNow(Lecture lecture);

    Mentee getCurrentMentee(Principal principal);
}
