package com.where.server.api.controller;


import com.where.server.domain.security.CustomUserDetails;
import com.where.server.api.service.auth.CustomUserDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping
    public ResponseEntity<CustomUserDetailDto> getAuthInfo(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok().body(CustomUserDetailDto.builder().mobile(user.getUsername()).role(user.getRole()).name(user.getNickName()).build());
    }

}
