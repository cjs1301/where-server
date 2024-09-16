package org.where.moduleapi.api.controller;

//import com.google.firebase.FirebaseApp;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.where.modulecore.domain.member.QMemberEntity;

@RestController
public class WarmupController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

//    @Autowired
//    private FirebaseApp firebaseApp;

    @GetMapping("/warmup")
    public String warmup() {
        // 데이터베이스 연결 테스트
        jdbcTemplate.execute("SELECT 1");

        // JPA/Hibernate 초기화
        entityManager.createNativeQuery("SELECT 1").getSingleResult();

        // QueryDSL 초기화
        jpaQueryFactory.selectFrom(QMemberEntity.memberEntity).fetchFirst();

        // Firebase 초기화 확인
//        firebaseApp.getOptions();

        // Flyway 마이그레이션 상태 확인
//        jdbcTemplate.queryForList("SELECT * FROM flyway_schema_history LIMIT 1");

        return "Warmup completed";
    }

}
