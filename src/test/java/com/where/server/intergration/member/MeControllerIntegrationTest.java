//package com.where.server.intergration.member;
//
//import com.where.server.api.service.follow.FollowRelationService;
//import com.where.server.api.service.member.MemberService;
//import com.where.server.api.service.member.dto.MemberDto;
//import com.where.server.domain.friend.FollowRelationEntity;
//import com.where.server.domain.friend.FollowRelationRepository;
//import com.where.server.domain.member.MemberEntity;
//import com.where.server.domain.member.MemberRepository;
//import com.where.server.domain.member.MemberRole;
//import com.where.server.domain.security.CustomUserDetails;
//import com.where.server.intergration.IntegrationTest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//class MeControllerIntegrationTest extends IntegrationTest {
//
//    @Autowired
//    private MemberService memberService;
//
//    @Autowired
//    private FollowRelationService followRelationService;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private FollowRelationRepository followRelationRepository;
//
//    private MemberEntity testMember;
//    private CustomUserDetails userDetails;
//
//    @BeforeEach
//    void setup() {
//        super.setUp();
//
//        // 테스트 멤버 생성
//        testMember = MemberEntity.builder()
//                .id(1L)
//                .name("Test User")
//                .phoneNumber("1234567890")
//                .isEnabled(true)
//                .role(MemberRole.ROLE_USER)
//                .isAppInstalled(true)
//                .isContactListSynchronized(false)
//                .build();
//
//        // 팔로잉할 멤버들 생성
//        MemberEntity followedMember1 = MemberEntity.builder()
//                .id(2L)
//                .name("Followed User 1")
//                .phoneNumber("+8210111111111")
//                .isEnabled(true)
//                .role(MemberRole.ROLE_USER)
//                .isAppInstalled(false)
//                .isContactListSynchronized(false)
//                .build();
//
//        MemberEntity followedMember2 = MemberEntity.builder()
//                .id(3L)
//                .name("Followed User 2")
//                .phoneNumber("+8210111111112")
//                .isEnabled(true)
//                .role(MemberRole.ROLE_USER)
//                .isAppInstalled(true)
//                .isContactListSynchronized(false)
//                .build();
//
//        List<MemberEntity> followedmembers = Arrays.asList(followedMember1, followedMember2);
//        // 멤버들 저장
//        memberRepository.save(testMember);
//        memberRepository.saveAll(followedmembers);
//
//        // 팔로잉 관계 설정
//        List<FollowRelationEntity> followRelations = followedmembers.stream()
//                .map(member ->
//                        FollowRelationEntity.builder()
//                                .follower(testMember)
//                                .following(member)
//                .build()
//                ).toList();
//        followRelationRepository.saveAll(followRelations);
//
//        userDetails = CustomUserDetails.fromMemberEntity(testMember);
//    }
//
//    @Test
//    @WithMockUser
//    @DisplayName("GET /me 동작 테스트")
//    void getMeInfo_shouldReturnMemberDto() throws Exception {
//
//        mockMvc.perform(get("/me")
//                        .with(user(userDetails))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value(testMember.getName()))
//                .andExpect(jsonPath("$.phoneNumber").value(testMember.getPhoneNumber()));
//    }
//
//    @Test
//    @WithMockUser
//    @DisplayName("GET /me/following 동작 테스트")
//    void getMyMyFollowing_shouldReturnSetOfMemberDto() throws Exception {
//        mockMvc.perform(get("/me/following")
//                        .with(user(userDetails))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[*].id").exists())
//                .andExpect(jsonPath("$[*].name").exists())
//                .andExpect(jsonPath("$[*].phoneNumber").exists())
//                .andExpect(jsonPath("$[*].enabled").exists());
//    }
//}
