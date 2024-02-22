package com.where.api.controller;

import com.where.api.dto.MessageDto;
import com.where.api.dto.LocationMessageDto;
import com.where.api.service.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessageSendingOperations template;
    private final ChannelService channelService;

    /*
        /topic/location/channels/12345      - 구독(channelId:12345)
        /topic/chat/channels/12345      - 구독(channelId:12345)
        /pub/location              - 메시지 발행
        /pub/chats              - 메시지 발행
    */

    @MessageMapping("/location")
    public void locationMessage(@Payload LocationMessageDto message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        channelService.createLocationMessage(message);
//        log.info(message.toString());
        template.convertAndSend("/topic/location" + message.getChannelId(), message);
    }
    @MessageMapping("/chat")
    public void message(@Payload MessageDto message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        channelService.createMessage(message);
//        log.info(message.toString());
        template.convertAndSend("/topic/chat" + message.getChannelId(), message);
    }
}
