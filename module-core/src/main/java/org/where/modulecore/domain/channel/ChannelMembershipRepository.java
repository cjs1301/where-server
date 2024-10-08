package org.where.modulecore.domain.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelMembershipRepository extends JpaRepository<ChannelMembershipEntity, UUID> {

    @Query("SELECT cm FROM ChannelMembershipEntity cm JOIN FETCH cm.channel c WHERE cm.member.id = :memberId")
    List<ChannelMembershipEntity> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("select f from ChannelMembershipEntity f where f.channel.id = ?1 and f.member.id = ?2")
    Optional<ChannelMembershipEntity> findByChannelIdAndMemberId(UUID channelId, Long memberId);

    @Modifying
    @Query("delete from ChannelMembershipEntity f where f.member.id = ?1")
    void deleteAllByMemberId(Long id);

    @Modifying
    @Query("update ChannelMembershipEntity f set f.connectionId = ?3 where f.channel.id = ?1 and f.member.id = ?2")
    void updateConnectionId(UUID channelId, Long memberId, String connectionId);

    @Modifying
    @Query("update ChannelMembershipEntity f set f.connectionId = null where f.connectionId = ?1")
    void removeConnectionId(String connectionId);

    @Query("select c from ChannelMembershipEntity c where c.connectionId = ?1")
    Optional<ChannelMembershipEntity> findByConnectionId(String connectionId);

    List<ChannelMembershipEntity> findAllByChannelId(UUID channelId);

    long countByChannel_Id(UUID id);

    int countByMemberId(Long memberId);
}
