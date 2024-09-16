package org.where.modulecore.domain.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
    List<MessageEntity> findAllByChannelId(UUID channelId);

    @Transactional
    @Modifying
    @Query("delete from MessageEntity m where m.channel.id = ?1")
    void deleteAllByChannelId(UUID channelId);

    @Transactional
    @Modifying
    @Query("delete from MessageEntity m where m.member.id = ?1")
    void deleteAllByMemberId(Long id);
}
