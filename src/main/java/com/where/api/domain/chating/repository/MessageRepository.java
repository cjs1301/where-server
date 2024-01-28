package com.where.api.domain.chating.repository;

import com.where.api.domain.chating.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    Optional<List<MessageEntity>> findAllByChannelId(UUID channel_id);
}
