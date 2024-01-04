package com.basic.backend.user.repository;

import com.basic.backend.user.domain.QUser;
import com.basic.backend.user.domain.User;
import com.basic.backend.user.domain.UserDto;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<User> findBySearch(UserDto.GetUsersReq search) {
        QUser user = QUser.user;

        JPQLQuery<User> q = queryFactory.selectFrom(user)
                .where(
                        user.userId.contains(search.getSearchText())
                );
        return q.fetch();
    }

}
