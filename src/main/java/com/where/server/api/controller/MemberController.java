package com.where.server.api.controller;

import com.where.server.api.service.auth.CustomUserDetails;
import com.where.server.api.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

//    @GetMapping
//    public ResponseEntity<MemberEntity> getMember(){
//        return memberService.getMember()
//    }

    @DeleteMapping
    public ResponseEntity<String> deleteMember(@AuthenticationPrincipal CustomUserDetails user){
        memberService.deleteMember(user.getId());
        return ResponseEntity.ok("삭제");
    }
}
