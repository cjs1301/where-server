package com.where.server.api.service.auth;

import com.where.server.api.service.member.MemberService;
import com.where.server.domain.member.MemberEntity;
import com.where.server.domain.member.MemberRepository;
import com.where.server.domain.member.MemberRole;
import com.where.server.domain.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService{
    @Autowired
    private MemberRepository memberRepository;

    public UserDetails loadUserByMobile(final String phoneNumber) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByPhoneNumber(phoneNumber)
                .orElseGet(() -> createMember(phoneNumber));
        return CustomUserDetails.fromMemberEntity(memberEntity);
    }

    private MemberEntity createMember(String phoneNumber) {
        MemberEntity memberEntity = MemberEntity.builder()
                .phoneNumber(phoneNumber)
                .isEnabled(true)
                .isAppInstalled(true)
                .isContactListSynchronized(false)
                .build();
        return memberRepository.save(memberEntity);
    }
}
