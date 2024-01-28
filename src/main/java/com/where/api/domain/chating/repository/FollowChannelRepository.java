package com.where.api.domain.chating.repository;

import com.where.api.domain.chating.entity.ChannelEntity;
import com.where.api.domain.chating.entity.FollowChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowChannelRepository extends JpaRepository<FollowChannelEntity, Long> {
    @Query("select f.channel from FollowChannelEntity f where f.member.id = ?1")
    Optional<List<ChannelEntity>> findAllByMemberId(Long memberId);
}
