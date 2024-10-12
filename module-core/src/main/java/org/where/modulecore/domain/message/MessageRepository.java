package org.where.modulecore.domain.message;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
    List<MessageEntity> findAllByChannelId(UUID channelId, Sort sort);

    @Transactional
    @Modifying
    @Query("delete from MessageEntity m where m.channel.id = ?1")
    void deleteAllByChannelId(UUID channelId);

    @Transactional
    @Modifying
    @Query("delete from MessageEntity m where m.member.id = ?1")
    void deleteAllByMemberId(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE MessageEntity m SET m.isRead = true WHERE m.channel.id = :channelId AND m.member.id != :memberId")
    int updateAllToReadExceptMember(@Param("channelId") UUID channelId, @Param("memberId") Long memberId);

    int countByMemberId(Long memberId);
}
