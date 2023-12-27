package com.where.api.domain.websocket;

import com.where.api.domain.websocket.dto.SocketMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebsocketController {

    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;
    /*
        /sub/channel/12345      - 구독(channelId:12345)
        /pub/location              - 메시지 발행
    */

    @MessageMapping("/location")
    public void message(SocketMessage message) {
        log.info("-------------------------------------------");
        log.info(message.toString());
        log.info("-------------------------------------------");
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }
}
