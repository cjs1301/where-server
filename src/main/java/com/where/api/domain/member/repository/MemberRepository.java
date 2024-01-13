package com.where.api.domain.member.repository;

import com.where.api.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Boolean existsByMobile(String mobile);

    MemberEntity findByMobile(String mobile);
}
