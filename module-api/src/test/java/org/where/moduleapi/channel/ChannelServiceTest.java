package org.where.moduleapi.channel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.where.moduleapi.api.service.channel.ChannelService;
import org.where.moduleapi.api.service.channel.dto.ChannelDto;
import org.where.moduleapi.api.service.channel.dto.MessageDto;
import org.where.modulecore.domain.channel.*;
import org.where.modulecore.domain.member.MemberEntity;
import org.where.modulecore.domain.member.MemberRepository;
import org.where.modulecore.domain.member.MemberRole;
import org.where.modulecore.domain.message.MessageEntity;
import org.where.modulecore.domain.message.MessageRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles(value = "test")
class ChannelServiceTest {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChannelMembershipRepository channelMembershipRepository;

    private  MemberEntity member1;
    private  MemberEntity member2;

    @BeforeEach
    void setUp() {
        // 기존 데이터 정리
        memberRepository.deleteAll();
        channelRepository.deleteAll();
        channelMembershipRepository.deleteAll();
        member1 = createAndSaveMember("+821011112222");
        member2 = createAndSaveMember("+821033332222");
    }
    @Test
    @DisplayName("getChannelMessageList : 채널의 상대 메세지를 읽음 처리합니다.")
    void getChannelMessageList_update_massage_isRead() {
        // Arrange
        ChannelDto channel = channelService.findOrCreateOneToOneChannel(
                member1.getId(),
                new ChannelDto.CreateOneToOneChannel(null, member2.getId())
        );

        // 메시지 생성 및 저장
        MessageEntity message1 = createAndSaveMessage(channel.getChannelId(), member1.getId(), "Hello from member1", false);
        MessageEntity message2 = createAndSaveMessage(channel.getChannelId(), member2.getId(), "Hello from member2", false);
        MessageEntity message3 = createAndSaveMessage(channel.getChannelId(), member2.getId(), "How are you?", false);

        // Act
        List<MessageDto> messages = channelService.getChannelMessageList(member1.getId(), channel.getChannelId());

        // Assert
        assertEquals(3, ((List<?>) messages).size(), "Should return all messages in the channel");

        // 메시지 상태 확인
        MessageEntity updatedMessage1 = messageRepository.findById(message1.getId()).orElseThrow();
        MessageEntity updatedMessage2 = messageRepository.findById(message2.getId()).orElseThrow();
        MessageEntity updatedMessage3 = messageRepository.findById(message3.getId()).orElseThrow();

        assertFalse(updatedMessage1.getIsRead(), "Member1's message should not be marked as read");
        assertTrue(updatedMessage2.getIsRead(), "Member2's message should be marked as read");
        assertTrue(updatedMessage3.getIsRead(), "Member2's message should be marked as read");
    }

    @Test
    @DisplayName("findOrCreateOneToOneChannel : 중복 된 채널을 생성하지 않습니다")
    void findOrCreateOneToOneChannel_ExistingChannel_ShouldReturnExistingOnoToOneChannel() {
        // Arrange
        // Create an initial channel
        ChannelDto initialChannel = channelService.findOrCreateOneToOneChannel(
                member1.getId(),
                new ChannelDto.CreateOneToOneChannel(null,member2.getId())
        );

        // Act
        ChannelDto result = channelService.findOrCreateOneToOneChannel(
                member1.getId(),
                new ChannelDto.CreateOneToOneChannel(null,member2.getId())
        );

        // Assert
        assertEquals(initialChannel.getChannelId(), result.getChannelId(),
                "Should return the same channel ID for existing channel");

        // Verify that no new channel was created
        long channelCount = channelRepository.countByMemberships_Member_Id(member1.getId());
        assertEquals(1, channelCount, "Should not create a new channel");

        // Verify channel memberships
        long membershipCount = channelMembershipRepository.countByChannel_Id(result.getChannelId());
        assertEquals(2, membershipCount, "Should have two memberships for the channel");
    }

    @Test
    @DisplayName("findOrCreateOneToOneChannel : 중복 된 채널이 없다면 새로운 채널을 생성합니다")
    void ffindOrCreateOneToOneChannel_NewChannel_ShouldCreateNewOnoToOneChannel() {
        // Act
        ChannelDto result = channelService.findOrCreateOneToOneChannel(
                member1.getId(),
                new ChannelDto.CreateOneToOneChannel(null,member2.getId())
        );

        // Assert
        assertNotNull(result.getChannelId(), "Should create a new channel with a valid ID");

        // Verify that a new channel was created
        long channelCount = channelRepository.countByMemberships_Member_Id(member1.getId());
        assertEquals(1, channelCount, "Should create one new channel");

        // Verify channel memberships
        long membershipCount = channelMembershipRepository.countByChannel_Id(result.getChannelId());
        assertEquals(2, membershipCount, "Should create two memberships for the new channel");
    }

    private MemberEntity createAndSaveMember(String phoneNumber) {
        MemberEntity member = MemberEntity.builder()
                .phoneNumber(phoneNumber)
                .role(MemberRole.ROLE_USER)
                .isEnabled(true)
                .isContactListSynchronized(false)
                .build();
        return memberRepository.save(member);
    }

    private MessageEntity createAndSaveMessage(UUID channelId, Long memberId, String content, boolean isRead) {
        ChannelEntity channel = channelRepository.findById(channelId).orElseThrow();
        MemberEntity member = memberRepository.findById(memberId).orElseThrow();

        MessageEntity message = MessageEntity.builder()
                .channel(channel)
                .member(member)
                .message(content)
                .isRead(isRead)
                .build();

        return messageRepository.save(message);
    }

}
