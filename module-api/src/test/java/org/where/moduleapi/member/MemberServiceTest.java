package org.where.moduleapi.member;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.where.moduleapi.BaseServiceTest;
import org.where.moduleapi.api.service.member.MemberService;
import org.where.moduleapi.api.service.member.dto.MemberDto;
import org.where.modulecore.domain.channel.ChannelMembershipRepository;
import org.where.modulecore.domain.member.MemberEntity;
import org.where.modulecore.domain.member.MemberRepository;
import org.where.modulecore.domain.member.MemberRole;
import org.where.modulecore.domain.message.MessageRepository;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest extends BaseServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChannelMembershipRepository channelMembershipRepository;

    private MemberEntity member;

    @BeforeEach
    void setUp() {
        // 기존 데이터 정리
        memberRepository.deleteAll();
        channelMembershipRepository.deleteAll();
        member = createAndSaveMember("+821011112222");
    }

    @Test
    void deleteMember_ShouldDeleteMemberAndAssociatedData() {
        // Given
        Long memberId = member.getId();

        // When
        memberService.deleteMember(memberId);

        // Then
        assertFalse(memberRepository.existsById(memberId));
        assertEquals(0, channelMembershipRepository.countByMemberId(memberId));
        assertEquals(0, messageRepository.countByMemberId(memberId));
    }

    @Test
    void getMember_ShouldReturnMemberDto() {
        // Given
        Long memberId = member.getId();

        // When
        MemberDto result = memberService.getMember(memberId);

        // Then
        assertNotNull(result);
        assertEquals(memberId, result.getId());
        assertEquals(member.getPhoneNumber(), result.getPhoneNumber());

    }

    @Test
    void getMember_ShouldThrowEntityNotFoundException_WhenMemberNotFound() {
        // Given
        Long nonExistentMemberId = 9999L;

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> memberService.getMember(nonExistentMemberId));
    }

    @Test
    void updateMember_ShouldUpdateAndReturnMemberDto() {
        // Given
        Long memberId = member.getId();
        MemberDto.Update updateData = MemberDto.Update.builder()
                .name("New Name")
                .profileImage("new_profile.jpg")
                .build();

        // When
        MemberDto result = memberService.updateMember(memberId, updateData);

        // Then
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("new_profile.jpg", result.getProfileImage());

        MemberEntity updatedMember = memberRepository.findById(memberId).orElseThrow();
        assertEquals("New Name", updatedMember.getName());
        assertEquals("new_profile.jpg", updatedMember.getProfileImage());
    }

    @Test
    void updateMember_ShouldThrowEntityNotFoundException_WhenMemberNotFound() {
        // Given
        Long nonExistentMemberId = 9999L;
        MemberDto.Update updateData = MemberDto.Update.builder()
                .name("New Name")
                .profileImage("new_profile.jpg")
                .build();

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> memberService.updateMember(nonExistentMemberId, updateData));
    }

    @Test
    void isRegisteredMember_ShouldReturnSetOfMemberDto() {
        // Given
        MemberEntity anotherMember = createAndSaveMember("+821033334444");
        MemberDto.Contact contact = MemberDto.Contact.builder().phoneNumberList(Arrays.asList(member.getPhoneNumber(), anotherMember.getPhoneNumber(), "+821055556666")).build();

        // When
        Set<MemberDto> result = memberService.isRegisteredMember(contact);

        // Then
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getPhoneNumber().equals(member.getPhoneNumber())));
        assertTrue(result.stream().anyMatch(dto -> dto.getPhoneNumber().equals(anotherMember.getPhoneNumber())));
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
