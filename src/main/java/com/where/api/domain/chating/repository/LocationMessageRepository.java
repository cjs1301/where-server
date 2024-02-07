package com.where.api.domain.chating.repository;

import com.where.api.domain.chating.entity.LocationMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LocationMessageRepository extends JpaRepository<LocationMessageEntity, Long> {
    @Transactional
    @Modifying
    @Query("delete from LocationMessageEntity l where l.member.id = ?1")
    void deleteAllByMemberId(Long id);
}
