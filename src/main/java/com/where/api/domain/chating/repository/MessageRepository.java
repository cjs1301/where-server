package com.where.api.domain.chating.repository;

import com.where.api.domain.chating.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
    Optional<List<MessageEntity>> findAllByChannelId(UUID channel_id);

    @Transactional
    @Modifying
    @Query("delete from MessageEntity m where m.channel.id = ?1")
    void deleteAllByChannelId(UUID channel_id);
}
