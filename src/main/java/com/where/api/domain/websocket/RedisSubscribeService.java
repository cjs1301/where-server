package com.where.api.domain.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.where.api.domain.websocket.dto.SocketMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscribeService implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());

            SocketMessage socketMessage = objectMapper.readValue(publishMessage, SocketMessage.class);

            messagingTemplate.convertAndSend("/sub/channel/" + socketMessage.getChannelId(), socketMessage);

        } catch (Exception e) {
            log.error("Exception {}", e.getMessage());
        }
    }
}
