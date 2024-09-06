package com.where.server.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JPAConfig {

    @PersistenceContext
    private EntityManager em;

    /**
     * JPAQueryFactory 를 bean으로 구성하여 매번 EntityManager로 부터 주입받는 부분을 줄임.
     *
     * @return JPAQueryFactory
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
