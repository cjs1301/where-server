package org.where.modulecore.domain.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository extends JpaRepository<ChannelEntity, UUID> {
    @Query("SELECT c FROM OneToOneChannelEntity c " +
            "WHERE c.id IN (SELECT cm.channel.id FROM ChannelMembershipEntity cm " +
            "               WHERE cm.member.id = :member1Id OR cm.member.id = :member2Id " +
            "               GROUP BY cm.channel.id " +
            "               HAVING COUNT(cm) = 2)")
    Optional<OneToOneChannelEntity> findOneToOneChannel(@Param("member1Id") Long member1Id,
                                                        @Param("member2Id") String member2Id);
}
