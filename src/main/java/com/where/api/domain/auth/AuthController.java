package com.where.api.domain.auth;


import com.where.api.config.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
/*
* join process
* */
    @GetMapping("/mobile/certification-number")
    public ResponseEntity<String> mobileNumberCertificationProcess(String mobileNumber) {
        if(!Objects.equals(mobileNumber, "01000000000")){
            authService.mobileNumberCertificationProcess(mobileNumber);
        }
        return ResponseEntity.ok().body("성공");
    }


    @GetMapping
    public ResponseEntity<CustomUserDetailDto> getAuthInfo(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok().body(CustomUserDetailDto.builder().mobile(user.getUsername()).role(user.getRole()).name(user.getNickName()).build());
    }

}
