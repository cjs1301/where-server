package org.where.moduleapi.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.where.moduleapi.api.service.auth.CustomUserDetailDto;
import org.where.moduleapi.api.service.auth.CustomUserDetails;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping
    public ResponseEntity<CustomUserDetailDto> getAuthInfo(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok().body(CustomUserDetailDto.builder().mobile(user.getUsername()).role(user.getRole()).name(user.getNickName()).build());
    }

}
