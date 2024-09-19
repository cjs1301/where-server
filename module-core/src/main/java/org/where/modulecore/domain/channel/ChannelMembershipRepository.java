package org.where.modulecore.domain.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelMembershipRepository extends JpaRepository<ChannelMembershipEntity, UUID> {

    @Query("select f from ChannelMembershipEntity f where f.member.id = ?1")
    List<ChannelMembershipEntity> findAllByMemberId(Long memberId);

    @Query("select (count(f) > 0) from ChannelMembershipEntity f where f.channel.id = ?1 and f.member.id = ?2")
    Boolean existsByChannelIdAndMemberId(UUID channelId, Long memberId);

    @Query("select f from ChannelMembershipEntity f where f.channel.id = ?1 and f.member.id = ?2")
    Optional<ChannelMembershipEntity> findByChannelIdAndMemberId(UUID channelId, Long memberId);

    @Transactional
    @Modifying
    @Query("delete from ChannelMembershipEntity f where f.member.id = ?1")
    void deleteAllByMemberId(Long id);

    @Query("select f from ChannelMembershipEntity f where f.channel.id = ?1 and f.connectionId is not null")
    List<ChannelMembershipEntity> findActiveConnectionsByChannelId(UUID channelId);

    @Modifying
    @Transactional
    @Query("update ChannelMembershipEntity f set f.connectionId = ?3 where f.channel.id = ?1 and f.member.id = ?2")
    void updateConnectionId(UUID channelId, Long memberId, String connectionId);

    @Modifying
    @Transactional
    @Query("update ChannelMembershipEntity f set f.connectionId = null where f.connectionId = ?1")
    void removeConnectionId(String connectionId);

    Optional<ChannelMembershipEntity> findByConnectionId(String connectionId);

    List<ChannelMembershipEntity> findAllByChannelId(UUID channelId);
}
