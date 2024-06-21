package com.where.backend.domain.channel.repository;

import com.where.backend.domain.channel.entity.ChatMessageEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.UUID;

public interface ChatMessageRepository extends R2dbcRepository<ChatMessageEntity, UUID> {
    Mono<List<ChatMessageEntity>> findAllByChannelId(UUID channelId);
//    List<MessageEntity> findAllByChannelId(UUID channelId);
//
//    @Transactional
//    @Modifying
//    @Query("delete from MessageEntity m where m.channel.id = ?1")
//    void deleteAllByChannelId(UUID channelId);
//
//    @Transactional
//    @Modifying
//    @Query("delete from MessageEntity m where m.member.id = ?1")
//    void deleteAllByMemberId(Long id);
}
