package _2024.winter.newssummation.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl {
    private final JPAQueryFactory jpaQueryFactory;
}
