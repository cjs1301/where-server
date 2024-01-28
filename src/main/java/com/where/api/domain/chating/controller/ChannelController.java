package com.where.api.domain.chating.controller;

import com.where.api.core.security.CustomUserDetails;
import com.where.api.domain.chating.dto.CreateChannelDto;
import com.where.api.domain.chating.dto.MessageDto;
import com.where.api.domain.chating.entity.ChannelEntity;
import com.where.api.domain.chating.entity.MessageEntity;
import com.where.api.domain.chating.service.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping
    public ResponseEntity<ChannelEntity> createChannel(@AuthenticationPrincipal CustomUserDetails user, @RequestBody String channelName){
        CreateChannelDto createChannelDto = new CreateChannelDto();
        createChannelDto.setChannelName(channelName);
        createChannelDto.setMemberMobileNumber(user.getUsername());
        return ResponseEntity.ok(channelService.createChannel(createChannelDto));
    }
    @GetMapping
    public ResponseEntity<List<ChannelEntity>> getChannelList(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(channelService.getMemberChannelList(user.getId()));
    }
    @GetMapping("/{channelId}/messages")
    public ResponseEntity<List<MessageDto>> getChannelMessageList(@PathVariable UUID channelId){
        return ResponseEntity.ok(channelService.getChannelMessageList(channelId));
    }

}
