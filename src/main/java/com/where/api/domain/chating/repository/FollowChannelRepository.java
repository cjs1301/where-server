package com.where.api.domain.chating.repository;

import com.where.api.domain.chating.entity.ChannelEntity;
import com.where.api.domain.chating.entity.FollowChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FollowChannelRepository extends JpaRepository<FollowChannelEntity, UUID> {
//    @Query("select f.channel from FollowChannelEntity f where f.member.id = ?1")
//    Optional<List<ChannelEntity>> findAllByMemberId(Long memberId);
@Query("select f from FollowChannelEntity f where f.member.id = ?1")
List<FollowChannelEntity> findAllByMemberId(Long memberId);

    @Query("select (count(f) > 0) from FollowChannelEntity f where f.channel.id = ?1 and f.member.id = ?2")
    Boolean existsByChannelIdAndMemberId(UUID channelId, Long memberId);

    @Query("select f from FollowChannelEntity f where f.channel.id = ?1 and f.member.id = ?2")
    FollowChannelEntity findByChannelIdAndMemberId(UUID channelId, Long memberId);
}
