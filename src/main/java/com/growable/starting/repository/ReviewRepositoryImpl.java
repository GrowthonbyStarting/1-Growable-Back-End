package com.growable.starting.repository;

import com.growable.starting.model.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ReviewRepositoryImpl extends QuerydslRepositorySupport implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public ReviewRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Review.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Review> findReviewsByMentorId(Long mentorId) {
        QReview review = QReview.review;
        QMentor mentor = QMentor.mentor;

        return queryFactory.selectFrom(review)
                .innerJoin(review.mentor, mentor)
                .where(mentor.mentorId.eq(mentorId))
                .fetch();
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Review deleteReview(Long reviewId) {
        QReview review = QReview.review;

        Review deletedReview = queryFactory.selectFrom(review)
                .where(review.id.eq(reviewId))
                .fetchOne();

        if (deletedReview != null) {
            entityManager.remove(deletedReview);
        }

        return deletedReview;
    }
}
