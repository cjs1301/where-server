package org.where.moduleapi.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.where.moduleapi.api.service.auth.CustomUserDetails;
import org.where.moduleapi.api.service.channel.ChannelService;
import org.where.moduleapi.api.service.channel.dto.ChannelDto;
import org.where.moduleapi.api.service.channel.dto.MessageDto;
import org.where.moduleapi.api.service.follow.FollowRelationDto;
import org.where.moduleapi.api.service.follow.FollowRelationService;
import org.where.moduleapi.api.service.member.MemberService;
import org.where.moduleapi.api.service.member.dto.MemberDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class MeController {

    private final MemberService memberService;

    private final FollowRelationService followRelationService;

    private final ChannelService channelService;

    @GetMapping
    public ResponseEntity<MemberDto> getMeInfo(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(memberService.getMember(user.getId()));
    }

    @PutMapping
    public ResponseEntity<MemberDto> updateMyInfo(@AuthenticationPrincipal CustomUserDetails user,@RequestBody MemberDto.Update body){
        return ResponseEntity.ok(memberService.updateMember(user.getId(),body));
    }

    @GetMapping("/following")
    public ResponseEntity<Set<MemberDto>> getMyMyFollowing(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(followRelationService.getMyFollowing(user.getId()));
    }

    @PostMapping("/following/synchronize")
    public ResponseEntity<Set<MemberDto>> synchronizeContactPhoneNumber(@AuthenticationPrincipal CustomUserDetails user, @RequestBody FollowRelationDto.CreateList body){
        return ResponseEntity.ok(followRelationService.createMyFollowRelationList(user.getId(), body));
    }
    @PostMapping("/following")
    public ResponseEntity<MemberDto> createMemberFollowing(@AuthenticationPrincipal CustomUserDetails user, @RequestBody FollowRelationDto.Create body){
        return ResponseEntity.ok(followRelationService.createMyFollowRelation(user.getId(), body));
    }
    @GetMapping("/channels")
    public ResponseEntity<List<ChannelDto>> getMyChannelList(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(channelService.getChannelMembershipList(user.getId()));
    }
    @GetMapping("/channels/{channelId}/messages")
    public ResponseEntity<List<MessageDto>> getChannelMessageList(@AuthenticationPrincipal CustomUserDetails user, @PathVariable UUID channelId){
        return ResponseEntity.ok(channelService.getChannelMessageList(user.getId(),channelId));
    }
}
