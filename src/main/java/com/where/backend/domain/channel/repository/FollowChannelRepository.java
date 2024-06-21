package com.where.backend.domain.channel.repository;

import com.where.backend.domain.channel.entity.ChannelEntity;
import com.where.backend.domain.channel.entity.FollowChannelEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
public interface FollowChannelRepository extends R2dbcRepository<FollowChannelEntity, UUID> {
    @Query("SELECT c.* FROM follow_channel fc JOIN channel c ON fc.channel_id = c.id WHERE fc.member_id = :memberId")
    Mono<List<ChannelEntity>> findAllChannelsByMemberId(Long memberId);

    Mono<Boolean> existsByChannelIdAndMemberId(UUID channelId, Long memberId); //    @Query("select f.channel from FollowChannelEntity f where f.member.id = ?1")

    Mono<FollowChannelEntity> findByChannelIdAndMemberId(UUID channelId, Long memberId); //    Optional<List<ChannelEntity>> findAllByMemberId(Long memberId);
//@Query("select f from FollowChannelEntity f where f.member.id = ?1")
//List<FollowChannelEntity> findAllByMemberId(Long memberId);
//
//    @Query("select (count(f) > 0) from FollowChannelEntity f where f.channel.id = ?1 and f.member.id = ?2")
//    Boolean existsByChannelIdAndMemberId(UUID channelId, Long memberId);
//
//    @Query("select f from FollowChannelEntity f where f.channel.id = ?1 and f.member.id = ?2")
//    FollowChannelEntity findByChannelIdAndMemberId(UUID channelId, Long memberId);
//
//    @Transactional
//    @Modifying
//    @Query("delete from FollowChannelEntity f where f.member.id = ?1")
//    void deleteAllByMemberId(Long id);
}
