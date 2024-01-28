package com.where.api.domain.chating.controller;

import com.where.api.core.security.CustomUserDetails;
import com.where.api.domain.chating.dto.LocationMessageDto;
import com.where.api.domain.chating.dto.MessageDto;
import com.where.api.domain.chating.service.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ChannelService channelService;

    /*
        /sub/location/channels/12345      - 구독(channelId:12345)
        /sub/chat/channels/12345      - 구독(channelId:12345)
        /pub/location              - 메시지 발행
        /pub/chats              - 메시지 발행
    */

//    @MessageMapping("/enter")
//    public void enterToChannel(MessageDto message){
//        simpMessageSendingOperations.convertAndSend("/sub/channels/" + message.getChannelId(),message);
//    }

    @MessageMapping("/location")
    public void locationMessage(LocationMessageDto message, @AuthenticationPrincipal CustomUserDetails user) {
        channelService.createLocationMessage(message,user.getId());
        log.info(message.toString());
        simpMessageSendingOperations.convertAndSend("/sub/location/channels/" + message.getChannelId(), message);
    }
    @MessageMapping("/chats")
    public void message(MessageDto message,@AuthenticationPrincipal CustomUserDetails user) {
        channelService.createMessage(message,user.getId());
        log.info(message.toString());
        simpMessageSendingOperations.convertAndSend("/sub/chat/channels/" + message.getChannelId(), message);
    }
}
