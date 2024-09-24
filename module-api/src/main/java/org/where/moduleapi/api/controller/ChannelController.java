package org.where.moduleapi.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.where.moduleapi.api.service.auth.CustomUserDetails;
import org.where.moduleapi.api.service.channel.ChannelService;
import org.where.moduleapi.api.service.channel.dto.ChannelDto;
import org.where.moduleapi.api.service.channel.dto.MessageDto;
import org.where.moduleapi.api.service.member.dto.MemberDto;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/channels")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @PostMapping
    public ResponseEntity<ChannelDto> findOrCreateChannelAndFollow(@AuthenticationPrincipal CustomUserDetails user, @RequestBody ChannelDto.CreateOneToOneChannel body){
        return ResponseEntity.ok(channelService.findOrCreateChannelAndFollow(user.getId(),body));
    }

    @DeleteMapping("/{channelId}/membership/{membershipId}")
    public ResponseEntity<String> deleteChannelMembership(@PathVariable UUID channelId,@PathVariable UUID membershipId){
        channelService.deleteChannelMembership(channelId, membershipId);
        return ResponseEntity.ok("삭제");
    }

    @GetMapping("/{channelId}/messages")
    public ResponseEntity<List<MessageDto>> getChannelMessageList(@PathVariable UUID channelId){
        return ResponseEntity.ok(channelService.getChannelMessageList(channelId));
    }

    @GetMapping("/{channelId}/members")
    public ResponseEntity<List<MemberDto>> getChannelMemberList(@PathVariable UUID channelId){
        return ResponseEntity.ok(channelService.getChannelMemberList(channelId));
    }

    @PostMapping("/{channelId}/membership")
    public ResponseEntity<ChannelDto> createChannelMembershipByChannelId(@PathVariable UUID channelId, @AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(channelService.createChannelMembership(channelId, user.getId()));
    }

}
