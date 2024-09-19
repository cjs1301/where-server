package org.where.moduleapi.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.where.moduleapi.api.service.auth.CustomUserDetails;
import org.where.moduleapi.api.service.member.MemberService;
import org.where.moduleapi.api.service.member.dto.MemberDto;

import java.util.Set;


@Slf4j
@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @DeleteMapping
    public ResponseEntity<String> deleteMember(@AuthenticationPrincipal CustomUserDetails user){
        memberService.deleteMember(user.getId());
        return ResponseEntity.ok("삭제");
    }

    @PostMapping("/registered")
    public ResponseEntity<Set<MemberDto>> inviteMember(@RequestBody MemberDto.PhoneNumberList body){
        return ResponseEntity.ok().body(memberService.isRegisteredMember(body));
    }
}
