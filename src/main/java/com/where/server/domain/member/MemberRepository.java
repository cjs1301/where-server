package com.where.server.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Boolean existsByPhoneNumber(String phoneNumber);

    MemberEntity findByPhoneNumber(String phoneNumber);
}
