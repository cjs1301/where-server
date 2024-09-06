package com.where.server.api.service.auth;

import com.where.server.api.service.member.MemberService;
import com.where.server.domain.member.MemberEntity;
import com.where.server.domain.member.MemberRepository;
import com.where.server.domain.member.MemberRole;
import com.where.server.domain.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService{
    private final MemberRepository memberRepository;

    public UserDetails loadUserByMobile(final String phoneNumber)throws UsernameNotFoundException {
        try {
            Boolean isMemberExist = this.existMemberByPhoneNumber(phoneNumber);
            CustomUserDetails userDetails;
            if (Boolean.TRUE.equals(isMemberExist)) {
                userDetails = this.findMember(phoneNumber);

            } else {
                userDetails = this.createMember(phoneNumber);
            }
            return userDetails;
        } catch (Exception e) {
            log.error("Unexpected error occurred while loading user for mobile: {}", phoneNumber, e);
            throw new UsernameNotFoundException("Error loading user for mobile: " + phoneNumber, e);
        }
    }

    private Boolean existMemberByPhoneNumber(String phoneNumber){
        return memberRepository.existsByPhoneNumber(phoneNumber);
    }

    private CustomUserDetails createMember(String phoneNumber){
        MemberEntity memberEntity = MemberEntity.builder()
                .phoneNumber(phoneNumber)
                .isEnabled(true)
                .isAppInstalled(true)
                .isContactListSynchronized(false)
                .build();
        memberRepository.save(memberEntity);
        return CustomUserDetails.fromMemberEntity(memberEntity);
    }
    private CustomUserDetails findMember(String phoneNumber){
        MemberEntity memberEntity = memberRepository.findByPhoneNumber(phoneNumber);
        return CustomUserDetails.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .phoneNumber(memberEntity.getPhoneNumber())
                .role(memberEntity.getRole().toString())
                .isEnabled(memberEntity.isEnabled())
                .build();
    }
}
