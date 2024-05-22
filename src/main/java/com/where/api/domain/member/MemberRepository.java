package com.where.api.domain.member;

import com.where.api.domain.member.MemberEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface MemberRepository extends R2dbcRepository<MemberEntity, Long> {
    Mono<Boolean> existsByMobile(String mobile);

    Mono<MemberEntity> findByMobile(String mobile);
}
