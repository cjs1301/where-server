package com.where.api.domain.chating.controller;

import com.where.api.domain.chating.dto.LocationChatMessageDto;
import com.where.api.domain.chating.entity.ChannelEntity;
import com.where.api.domain.chating.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ChatService chatService;
    /*
        /sub/channel/12345      - 구독(channelId:12345)
        /pub/location              - 메시지 발행
    */

    @MessageMapping("/location")
    public void locationMessage(LocationChatMessageDto message) {
        log.info("-------------------------------------------");
        log.info(message.toString());
        log.info("-------------------------------------------");
        simpMessageSendingOperations.convertAndSend("/sub/channels/" + message.getChannelId(), message);
    }
    @MessageMapping("/chat")
    public void message(LocationChatMessageDto message) {
        log.info("-------------------------------------------");
        log.info(message.toString());
        log.info("-------------------------------------------");
        simpMessageSendingOperations.convertAndSend("/sub/channels/" + message.getChannelId(), message);
    }

    @PostMapping("/channels")
    public ResponseEntity<ChannelEntity> createChannel(){

        return ResponseEntity.ok(chatService.createChannel());
    }
}
