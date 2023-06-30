package com.growable.starting.repository;

import com.growable.starting.model.QUser;
import com.growable.starting.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        super(User.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<User> findByKakaoId(Long kakaoId) {
        QUser user = QUser.user;

        User resultUser = queryFactory.selectFrom(user)
                .where(user.kakaoId.eq(kakaoId))
                .fetchOne();

        return Optional.ofNullable(resultUser);
    }
}
