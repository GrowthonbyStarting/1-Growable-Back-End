package com.growable.starting.service;

import com.growable.starting.dto.ReviewDto;
import com.growable.starting.model.*;
import com.growable.starting.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final MenteeRepository menteeRepository;
    private final ReviewRepository reviewRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ReplyRepository replyRepository;
    private final MentorRepository mentorRepository;

    @Autowired
    public ReviewServiceImpl(MenteeRepository menteeRepository, ReviewRepository reviewRepository, EnrollmentRepository enrollmentRepository, ReplyRepository replyRepository, MentorRepository mentorRepository) {
        this.menteeRepository = menteeRepository;
        this.reviewRepository = reviewRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.replyRepository = replyRepository;
        this.mentorRepository = mentorRepository;
    }

    @Transactional
    @Override
    // 리뷰를 생성하는 메소드
    public Pair<Review, Mentor> createReview(ReviewDto reviewDto, Long lectureId, Long menteeId) {
        // 강좌와 멘티의 정보로 등록 여부 확인
        Optional<Enrollment> enrollment = enrollmentRepository.findByLectureIdAndMenteeId(lectureId, menteeId);

        // 강좌 참여 여부와 강좌 종료 시간을 확인하여 리뷰 저장
        if (enrollment.isPresent() && isLectureEndDateAfterNow(enrollment.get().getLecture())) {
            Review review = new Review();
            review.setContent(reviewDto.getContent());
            review.setStarScore(reviewDto.getStarScore());
            reviewRepository.save(review);

            double avgStarScore = mentorRepository.avgStarScore(review.getMentor().getMentorId());
            Mentor mentor = review.getMentor();
            mentor.setStarScore(avgStarScore);

            mentorRepository.save(mentor); // Mentor 엔티티를 업데이트

            return Pair.of(review, mentor);
        } else {
            throw new RuntimeException("Cannot submit the review: The mentee has not attended the lecture or the lecture is not finished yet.");
        }
    }


    // 멘토별 리뷰를 가져오는 메소드
    @Transactional
    @Override
    public List<Review> getReviewsForMentor(Long mentorId) {
        return reviewRepository.findReviewsByMentorId(mentorId);
    }

    // 강좌 종료 시간이 현재 시간보다 이후인지 확인하는 메소드
    @Transactional
    @Override
    public boolean isLectureEndDateAfterNow(Lecture lecture) {
        return lecture.getLectureEndDate().isAfter(LocalDate.now());
    }

    @Transactional
    @Override
    public Review deleteReviews(Long reviewId) {
        return reviewRepository.deleteReview(reviewId);
    }

    @Transactional
    @Override
    public Reply addReply(Long reviewId, Long mentorId, String content) {
        Mentor mentor = mentorRepository.findById(mentorId).orElseThrow(() -> new IllegalArgumentException("Invalid mentorId: " + mentorId));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("Invalid reviewId: " + reviewId));

        if (mentor.getLectures().stream().noneMatch(lecture -> lecture.getId().equals(review.getLecture().getId()))) {
            throw new IllegalArgumentException("Mentor is not the creator of the lecture associated with the review");
        }

        Reply reply = new Reply();
        reply.setContent(content);
        reply.setReview(review);
        reply.setMentor(mentor);

        replyRepository.save(reply);
        return reply;
    }

}
