package org.where.modulecore.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Boolean existsByPhoneNumber(String phoneNumber);

    @Query("select m from MemberEntity m where m.phoneNumber = ?1")
    Optional<MemberEntity> findByPhoneNumber(String phoneNumber);

    @Query("select m from MemberEntity m where m.phoneNumber in ?1")
    Set<MemberEntity> findAllByPhoneNumberIn(Set<String> uniquePhoneNumbers);
}
