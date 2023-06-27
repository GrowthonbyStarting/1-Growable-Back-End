package com.growable.starting.repository;

import com.growable.starting.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.lecture.id = :lectureId")
    List<Review> findAllByLectureId(@Param("lectureId") Long lectureId);

    @Query("SELECT r FROM Review r WHERE r.mentor.mentorId = :mentorId")
    List<Review> findReviewsByMentorId(@Param("mentorId") Long mentorId);

    @Query("SELECT r FROM Review r WHERE r.id = :reviewId")
    Review findReviewById(@Param("reviewId") Long reviewId);

    @Query(value = "DELETE from Review WHERE Id = ?", nativeQuery = true)
    Review deleteReview(Long reviewId);
}