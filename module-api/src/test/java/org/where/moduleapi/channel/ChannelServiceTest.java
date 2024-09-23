package org.where.moduleapi.channel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.where.moduleapi.api.service.channel.ChannelService;
import org.where.moduleapi.api.service.channel.dto.ChannelDto;
import org.where.modulecore.domain.channel.*;
import org.where.modulecore.domain.member.MemberEntity;
import org.where.modulecore.domain.member.MemberRepository;
import org.where.modulecore.domain.member.MemberRole;

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
    private ChannelMembershipRepository channelMembershipRepository;


    @Test
    void findOrCreateChannelAndFollow_ExistingChannel_ShouldReturnExistingChannel() {
        // Arrange
        MemberEntity member1 = createAndSaveMember("+821011112222");
        MemberEntity member2 = createAndSaveMember("+821033332222");

        // Create an initial channel
        ChannelDto initialChannel = channelService.findOrCreateChannelAndFollow(
                member1.getId(),
                new ChannelDto.CreateOneToOneChannel(null,member2.getId())
        );

        // Act
        ChannelDto result = channelService.findOrCreateChannelAndFollow(
                member1.getId(),
                new ChannelDto.CreateOneToOneChannel(null,member2.getId())
        );

        // Assert
        assertEquals(initialChannel.getChannelId(), result.getChannelId(),
                "Should return the same channel ID for existing channel");

        // Verify that no new channel was created
        long channelCount = channelRepository.count();
        assertEquals(1, channelCount, "Should not create a new channel");

        // Verify channel memberships
        long membershipCount = channelMembershipRepository.count();
        assertEquals(2, membershipCount, "Should have two memberships for the channel");
    }

    @Test
    void findOrCreateChannelAndFollow_NewChannel_ShouldCreateNewChannel() {
        // Arrange
        MemberEntity member1 = createAndSaveMember("+821011112222");
        MemberEntity member2 = createAndSaveMember("+821033332222");

        // Act
        ChannelDto result = channelService.findOrCreateChannelAndFollow(
                member1.getId(),
                new ChannelDto.CreateOneToOneChannel(null,member2.getId())
        );

        // Assert
        assertNotNull(result.getChannelId(), "Should create a new channel with a valid ID");

        // Verify that a new channel was created
        long channelCount = channelRepository.count();
        assertEquals(1, channelCount, "Should create one new channel");

        // Verify channel memberships
        long membershipCount = channelMembershipRepository.count();
        assertEquals(2, membershipCount, "Should create two memberships for the new channel");
    }

    private MemberEntity createAndSaveMember(String phoneNumber) {
        MemberEntity member = MemberEntity.builder()
                .phoneNumber(phoneNumber)
                .role(MemberRole.ROLE_USER)
                .isEnabled(true)
                .isRegistered(true)
                .isContactListSynchronized(false)
                .build();
        return memberRepository.save(member);
    }
}
