package com.where.server.api.service.auth;

import com.where.server.api.service.member.MemberService;
import com.where.server.domain.member.MemberEntity;
import com.where.server.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(final String mobile)throws UsernameNotFoundException {
        log.info("Loading user details for mobile: {}", mobile);
        try {
            Boolean isMemberExist = memberService.existMember(mobile);
            log.info("Member exists: {}", isMemberExist);
            if (Boolean.TRUE.equals(isMemberExist)) {
                CustomUserDetails userDetails = memberService.findMember(mobile);
                log.info("Found existing member: {}", userDetails.toString());
                return userDetails;
            } else {
                CustomUserDetails userDetails = memberService.createMember(mobile);
                log.info("Created new member: {}", userDetails.toString());
                return userDetails;
            }
        } catch (Exception e) {
            log.error("Error loading user details", e);
            throw e;
        }
    }
}
