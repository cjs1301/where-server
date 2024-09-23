package org.where.modulecore.domain.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository extends JpaRepository<ChannelEntity, UUID> {
    @Query("SELECT c FROM OneToOneChannelEntity c WHERE " +
            "(c.member1Id = :member1Id AND c.member2Id = :member2Id) OR " +
            "(c.member1Id = :member2Id AND c.member2Id = :member1Id)")
    Optional<OneToOneChannelEntity> findOneToOneChannel(@Param("member1Id") Long member1Id,
                                                        @Param("member2Id") Long member2Id);
}
