package com.growable.starting.repository;

import com.growable.starting.model.Mentee;
import com.growable.starting.model.QMentee;
import com.growable.starting.model.QUser;
import com.growable.starting.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MenteeRepositoryImpl extends QuerydslRepositorySupport implements MenteeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public MenteeRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Mentee.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<Mentee> findByEmail(String email) {
        QMentee mentee = QMentee.mentee;
        QUser user = QUser.user;

        return Optional.ofNullable(queryFactory.selectFrom(mentee)
                .innerJoin(mentee.user, user)
                .where(user.kakaoEmail.eq(email))
                .fetchFirst());
    }

    @Override
    public Mentee findByUser(User authenticatedUser) {
        QMentee mentee = QMentee.mentee;
        QUser user = QUser.user;

        return queryFactory.selectFrom(mentee)
                .innerJoin(mentee.user, user)
                .where(user.eq(authenticatedUser))
                .fetchFirst();
    }
}
