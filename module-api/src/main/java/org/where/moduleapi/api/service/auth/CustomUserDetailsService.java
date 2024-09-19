package org.where.moduleapi.api.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.where.modulecore.domain.member.MemberEntity;
import org.where.modulecore.domain.member.MemberRepository;
import org.where.modulecore.domain.member.MemberRole;

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
                .role(MemberRole.ROLE_USER)
                .isEnabled(true)
                .isRegistered(true)
                .isContactListSynchronized(false)
                .build();
        return memberRepository.save(memberEntity);
    }
}
