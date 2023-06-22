package com.growable.starting.service;

import com.growable.starting.model.Enrollment;
import com.growable.starting.model.Lecture;
import com.growable.starting.model.Mentee;
import com.growable.starting.model.Review;
import com.growable.starting.repository.EnrollmentRepository;
import com.growable.starting.repository.MenteeRepository;
import com.growable.starting.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final MenteeRepository menteeRepository;
    private final ReviewRepository reviewRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public ReviewServiceImpl(MenteeRepository menteeRepository, ReviewRepository reviewRepository, EnrollmentRepository enrollmentRepository) {
        this.menteeRepository = menteeRepository;
        this.reviewRepository = reviewRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // 리뷰를 생성하는 메소드
    @Override
    public Review createReview(Review review, Principal principal) {
        Mentee currentMentee = getCurrentMentee(principal);

        // 강좌와 멘티의 정보로 등록 여부 확인
        Optional<Enrollment> enrollment = enrollmentRepository.findByLectureIdAndMenteeId(
                review.getLecture().getId(),
                currentMentee.getMenteeId()
        );

        // 강좌 참여 여부와 강좌 종료 시간을 확인하여 리뷰 저장
        if (enrollment.isPresent() && isLectureEndDateAfterNow(enrollment.get().getLecture())) {
            review.setMentee(currentMentee);
            review.setContent("your_content_here");
            return reviewRepository.save(review);
        } else {
            throw new RuntimeException("Cannot submit the review: The mentee has not attended the lecture or the lecture is not finished yet.");
        }
    }

    // 강좌별 리뷰를 가져오는 메소드
    @Override
    public List<Review> getReviewsForLecture(Long lectureId) {
        return reviewRepository.findAllByLectureId(lectureId);
    }

    // 멘토별 리뷰를 가져오는 메소드
    @Override
    public List<Review> getReviewsForMentor(Long mentorId) {
        return reviewRepository.findReviewsByMentorId(mentorId);
    }

    // 강좌 종료 시간이 현재 시간보다 이후인지 확인하는 메소드
    @Override
    public boolean isLectureEndDateAfterNow(Lecture lecture) {
        return lecture.getLectureEndDate().isAfter(LocalDate.now());
    }

    // 현재 접속한 멘티 정보를 가져오는 메소드
    @Override
    public Mentee getCurrentMentee(Principal principal) {
        if (principal instanceof DefaultOAuth2User userDetails) {

            // 이메일 정보를 가져옴
            String email = (String) userDetails.getAttributes().get("email");
            if (email != null) {
                Optional<Mentee> optionalMentee = menteeRepository.findByEmail(email);
                if (optionalMentee.isPresent()) {
                    return optionalMentee.get();
                } else {
                    throw new RuntimeException("Mentee not found.");
                }
            } else {
                throw new RuntimeException("Email not found in OAuth2 user attributes.");
            }
        } else {
            throw new RuntimeException("No valid principal found.");
        }
    }
}
