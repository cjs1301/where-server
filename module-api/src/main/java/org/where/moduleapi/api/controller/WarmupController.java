package org.where.moduleapi.api.controller;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.where.modulecore.domain.member.QMemberEntity;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WarmupController {

    private final JdbcTemplate jdbcTemplate;

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;



    @GetMapping("/warmup")
    public String warmup() {
        // 데이터베이스 연결 테스트
        jdbcTemplate.execute("SELECT 1");

        // JPA/Hibernate 초기화
        entityManager.createNativeQuery("SELECT 1").getSingleResult();

        // QueryDSL 초기화
        jpaQueryFactory.selectFrom(QMemberEntity.memberEntity).fetchFirst();

        return "Warmup completed";
    }

}
