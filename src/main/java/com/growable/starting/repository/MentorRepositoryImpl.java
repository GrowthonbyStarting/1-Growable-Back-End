package com.growable.starting.repository;

import com.growable.starting.model.Mentor;
import com.growable.starting.model.QMentor;
import com.growable.starting.model.QReview;
import com.growable.starting.model.QUser;
import com.growable.starting.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MentorRepositoryImpl extends QuerydslRepositorySupport implements MentorRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public MentorRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Mentor.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public double avgStarScore(Long mentorId) {
        QReview review = QReview.review;
        QMentor mentor = QMentor.mentor;

        Double avgStarScore = queryFactory.select(review.starScore.avg())
                .from(review)
                .where(review.mentor.mentorId.eq(mentorId))
                .fetchOne();
        return avgStarScore == null ? 0.0 : avgStarScore;
    }

    @Override
    public Mentor findByUser(User authenticatedUser) {
        QMentor mentor = QMentor.mentor;
        QUser user = QUser.user;

        return queryFactory.selectFrom(mentor)
                .innerJoin(mentor.user, user)
                .where(user.eq(authenticatedUser))
                .fetchFirst();
    }

    @Override
    public Optional<Mentor> findMentorWithCompanyInfos(Long mentorId) {
        QMentor mentor = QMentor.mentor;

        return Optional.ofNullable(queryFactory.selectFrom(mentor)
                .leftJoin(mentor.companyInfos).fetchJoin()
                .where(mentor.mentorId.eq(mentorId))
                .fetchOne());
    }

    @Override
    public Optional<Mentor> findMentorWithLectureExperiences(Long mentorId) {
        QMentor mentor = QMentor.mentor;

        return Optional.ofNullable(queryFactory.selectFrom(mentor)
                .leftJoin(mentor.lectureExperiences).fetchJoin()
                .where(mentor.mentorId.eq(mentorId))
                .fetchOne());
    }
}
