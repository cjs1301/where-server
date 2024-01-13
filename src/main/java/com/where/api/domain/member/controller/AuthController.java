package com.where.api.domain.member.controller;


import com.where.api.domain.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


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
        authService.mobileNumberCertificationProcess(mobileNumber);
        return ResponseEntity.ok().body("성공");
    }


    @GetMapping
    public ResponseEntity<String> getAuthInfo(){
        return ResponseEntity.ok().body(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
