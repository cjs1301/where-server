package com.where.api.domain.chating.service;


import com.where.api.domain.chating.dto.MessageDto;
import com.where.api.domain.chating.entity.ChannelEntity;
import com.where.api.domain.chating.entity.MessageEntity;
import com.where.api.domain.chating.repository.MessageRepository;
import com.where.api.domain.member.entity.MemberEntity;
import com.where.api.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumer {
    private final SimpMessageSendingOperations messagingTemplate;
//    private final RabbitTemplate rabbitTemplate;
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    /**
     * Kafka에서 메시지가 발행(publish)되면 대기하고 있던 Kafka Consumer가 해당 메시지를 받아 처리한다.
     */
    @KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void sendMessage(MessageDto messageDto ) {
        try {
            MemberEntity member = memberRepository.findByMobile(messageDto.getSender());
            MessageEntity message = MessageEntity.builder()
                    .message(messageDto.getMessage())
                    .member(member)
                    .channel(ChannelEntity.builder().id(UUID.fromString(messageDto.getChannelId())).build())
                    .build();
            messageRepository.save(message);
            messagingTemplate.convertAndSend("/topic/chat/channels/" + messageDto.getChannelId(), messageDto); // Websocket 구독자에게 채팅 메시지 Send
//            rabbitTemplate.convertAndSend("/sub/chat/channels/" + messageDto.getChannelId(), messageDto);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
