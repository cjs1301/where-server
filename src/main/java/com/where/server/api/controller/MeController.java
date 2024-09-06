package com.where.server.api.controller;

import com.where.server.api.service.follow.FollowRelationDto;
import com.where.server.api.service.follow.FollowRelationService;
import com.where.server.domain.security.CustomUserDetails;
import com.where.server.api.service.member.MemberService;
import com.where.server.api.service.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/me")
public class MeController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private FollowRelationService followRelationService;

    @GetMapping
    public ResponseEntity<MemberDto> getMeInfo(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(memberService.getMember(user.getId()));
    }

    @GetMapping("/following")
    public ResponseEntity<Set<MemberDto>> getMyMyFollowing(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(followRelationService.getMyFollowing(user.getId()));
    }

    @PostMapping("/following")
    public ResponseEntity<Set<MemberDto>> synchronizeContactPhoneNumber(@AuthenticationPrincipal CustomUserDetails user, @RequestBody FollowRelationDto.Create body){
        return ResponseEntity.ok(followRelationService.createMyFollowRelation(user.getId(), body));
    }
}
