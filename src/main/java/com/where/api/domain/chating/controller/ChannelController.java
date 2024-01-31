package com.where.api.domain.chating.controller;

import com.where.api.core.security.CustomUserDetails;
import com.where.api.domain.chating.dto.CreateChannelDto;
import com.where.api.domain.chating.dto.FollowChannelDto;
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
    public ResponseEntity<FollowChannelDto> createChannelAndFollow(@AuthenticationPrincipal CustomUserDetails user, @RequestBody String channelName){
        CreateChannelDto createChannelDto = new CreateChannelDto();
        createChannelDto.setChannelName(channelName);
        createChannelDto.setMemberMobileNumber(user.getUsername());
        return ResponseEntity.ok(channelService.createChannelAndFollow(createChannelDto));
    }
    @DeleteMapping("/{channelId}/follow/{followId}")
    public ResponseEntity<String> deleteFollowChannelAndChannel(@PathVariable UUID channelId,@PathVariable UUID followId){
        channelService.deleteFollowChannel(channelId,followId);
        return ResponseEntity.ok("삭제");
    }
    @GetMapping("follow")
    public ResponseEntity<List<FollowChannelDto>> getFollowChannelList(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(channelService.getFollowChannelList(user.getId()));
    }
    @GetMapping("/{channelId}/messages")
    public ResponseEntity<List<MessageDto>> getChannelMessageList(@PathVariable UUID channelId){
        return ResponseEntity.ok(channelService.getChannelMessageList(channelId));
    }

    @PostMapping("/{channelId}/follow")
    public ResponseEntity<FollowChannelDto> createFollowChannel(@PathVariable UUID channelId,@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(channelService.createFollowChannel(channelId,user.getId()));
    }

}
