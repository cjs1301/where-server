package com.where.server.intergration.member;

import com.where.server.domain.member.MemberEntity;
import com.where.server.domain.member.MemberRepository;
import com.where.server.domain.security.CustomUserDetails;
import com.where.server.intergration.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class MemberIntegrationTest extends IntegrationTest {

    @Autowired
    private MemberRepository memberRepository;


    private MemberEntity testMember;
    private CustomUserDetails userDetails;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }



    @Test
    void deleteMember_shouldDeleteMemberAndReturnOkStatus() throws Exception {
        mockMvc.perform(delete("/members")
                        .with(user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("삭제"));

        // Verify that the member has been deleted
        mockMvc.perform(get("/members/me")
                        .with(user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
