package com.growable.starting.repository;

import com.growable.starting.model.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class EnrollmentRepositoryImpl extends QuerydslRepositorySupport implements EnrollmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public EnrollmentRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Enrollment.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<Enrollment> findByMenteeAndLecture(Mentee mentee, Lecture lecture) {
        QEnrollment enrollment = QEnrollment.enrollment;
        QMentee qMentee = QMentee.mentee;
        QLecture qLecture = QLecture.lecture;

        return Optional.ofNullable(queryFactory.selectFrom(enrollment)
                .innerJoin(enrollment.mentee, qMentee)
                .innerJoin(enrollment.lecture, qLecture)
                .where(qMentee.eq(mentee), qLecture.eq(lecture))
                .fetchFirst());
    }

    @Override
    public Optional<Enrollment> findByLectureIdAndMenteeId(Long lectureId, Long menteeId) {
        QEnrollment enrollment = QEnrollment.enrollment;
        QMentee mentee = QMentee.mentee;
        QLecture lecture = QLecture.lecture;

        return Optional.ofNullable(queryFactory.selectFrom(enrollment)
                .innerJoin(enrollment.mentee, mentee)
                .innerJoin(enrollment.lecture, lecture)
                .where(lecture.id.eq(lectureId), mentee.menteeId.eq(menteeId))
                .fetchFirst());
    }

    @Override
    public List<Enrollment> findByLectureIdAndMentorId(Long lectureId, Long mentorId) {
        QEnrollment enrollment = QEnrollment.enrollment;
        QLecture lecture = QLecture.lecture;
        QMentor mentor = QMentor.mentor;

        return queryFactory.selectFrom(enrollment)
                .innerJoin(enrollment.lecture, lecture)
                .innerJoin(enrollment.mentor, mentor)
                .where(lecture.id.eq(lectureId), mentor.mentorId.eq(mentorId))
                .fetch();
    }
}
