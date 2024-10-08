package org.where.moduleapi.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.where.moduleapi.BaseServiceTest;
import org.where.moduleapi.api.service.auth.CustomUserDetails;
import org.where.moduleapi.api.service.auth.CustomUserDetailsService;
import org.where.modulecore.domain.member.MemberEntity;
import org.where.modulecore.domain.member.MemberRepository;
import org.where.modulecore.domain.member.MemberRole;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailServiceTest extends BaseServiceTest {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private MemberRepository memberRepository;

    private MemberEntity member;

    @BeforeEach
    void setUp() {
        // 기존 데이터 정리
        memberRepository.deleteAll();

        member = createAndSaveMember("+821011112222");
    }

    @Test
    @DisplayName("loadUserByMobile : 전화번호를 받아 유저정보를 불러옵니다.")
    void loadUserByMobileIsCreateMember(){
        UserDetails userDetails = customUserDetailsService.loadUserByMobile("+821011112222");
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        assertNotNull(customUserDetails);

        assertEquals(member.getId(),customUserDetails.getId(),"유저 (아이디) 정보가 일치하지 않습니다.");
        assertEquals(member.getRole().name(),customUserDetails.getRole(),"유저 (권한) 정보가 일치하지 않습니다.");
        assertTrue(customUserDetails.isEnabled(), "유저 (활성화) 정보가 일치하지 않습니다.");
    }

    private MemberEntity createAndSaveMember(String phoneNumber) {
        MemberEntity memberEntity = MemberEntity.builder()
                .phoneNumber(phoneNumber)
                .role(MemberRole.ROLE_USER)
                .isEnabled(true)
                .isContactListSynchronized(false)
                .build();
        return memberRepository.save(memberEntity);
    }
}
