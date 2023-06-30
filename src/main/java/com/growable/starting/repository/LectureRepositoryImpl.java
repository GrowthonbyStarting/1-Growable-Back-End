package com.growable.starting.repository;

import com.growable.starting.model.QEnrollment;
import com.growable.starting.model.QLecture;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class LectureRepositoryImpl extends QuerydslRepositorySupport implements LectureRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public LectureRepositoryImpl(JPAQueryFactory queryFactory) {
        super(QLecture.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public int countEnrolledStudentsForCurrentLecture(Long lectureId) {
        QEnrollment enrollment = QEnrollment.enrollment;
        QLecture lecture = QLecture.lecture;

        return (int) queryFactory.selectFrom(enrollment)
                .innerJoin(enrollment.lecture, lecture)
                .where(lecture.id.eq(lectureId), lecture.recruitmentEndDate.gt(LocalDate.now()))
                .fetchCount();
    }
}
