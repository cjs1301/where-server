package com.where.server.api.controller;

import com.where.server.domain.security.CustomUserDetails;
import com.where.server.api.service.member.MemberService;
import com.where.server.api.service.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


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
}
