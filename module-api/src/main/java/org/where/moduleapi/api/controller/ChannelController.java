package org.where.moduleapi.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.where.moduleapi.api.service.auth.CustomUserDetails;
import org.where.moduleapi.api.service.channel.ChannelService;
import org.where.moduleapi.api.service.channel.dto.CreateChannelDto;
import org.where.moduleapi.api.service.channel.dto.FollowChannelDto;
import org.where.moduleapi.api.service.channel.dto.MessageDto;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/channels")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @PostMapping
    public ResponseEntity<FollowChannelDto> createChannelAndFollow(@AuthenticationPrincipal CustomUserDetails user, @RequestBody CreateChannelDto body){
        return ResponseEntity.ok(channelService.createChannelAndFollow(body.getChannelName(),user.getPhoneNumber()));
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
