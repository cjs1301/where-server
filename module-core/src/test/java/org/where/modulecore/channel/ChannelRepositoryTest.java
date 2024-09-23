package org.where.modulecore.channel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.where.modulecore.QuerydslTestConfig;
import org.where.modulecore.domain.channel.ChannelMembershipEntity;
import org.where.modulecore.domain.channel.ChannelMembershipRepository;
import org.where.modulecore.domain.channel.ChannelRepository;
import org.where.modulecore.domain.channel.OneToOneChannelEntity;
import org.where.modulecore.domain.member.MemberEntity;
import org.where.modulecore.domain.member.MemberRepository;
import org.where.modulecore.domain.member.MemberRole;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslTestConfig.class)
class ChannelRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ChannelMembershipRepository channelMembershipRepository;

    private MemberEntity member1;
    private MemberEntity member2;
    private OneToOneChannelEntity channel;

    @BeforeEach
    void setUp() {
        member1 = MemberEntity.builder()
                .name("Member 1")
                .isEnabled(true)
                .phoneNumber("1234567890")
                .profileImage("profile1.jpg")
                .isRegistered(true)
                .isContactListSynchronized(true)
                .role(MemberRole.ROLE_USER)
                .build();

        member2 = MemberEntity.builder()
                .name("Member 2")
                .isEnabled(true)
                .phoneNumber("0987654321")
                .profileImage("profile2.jpg")
                .isRegistered(true)
                .isContactListSynchronized(true)
                .role(MemberRole.ROLE_USER)
                .build();
        memberRepository.saveAll(List.of(member1,member2));

        // Create a one-to-one channel
        channel = OneToOneChannelEntity.builder()
                .member1Id(member1.getId())
                .member2Id(member2.getId())
                .build();
        channelRepository.save(channel);

        // Create channel memberships
        ChannelMembershipEntity membership1 = ChannelMembershipEntity.builder()
                .channel(channel)
                .member(member1)
                .build();

        ChannelMembershipEntity membership2 = ChannelMembershipEntity.builder()
                .channel(channel)
                .member(member2)
                .build();
        channelMembershipRepository.saveAll(List.of(membership1,membership2));
    }

    @Test
    void findOneToOneChannel_ShouldReturnChannel_WhenChannelExists() {
        Optional<OneToOneChannelEntity> result = channelRepository.findOneToOneChannel(member1.getId(), member2.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(channel.getId());
    }

    @Test
    void findOneToOneChannel_ShouldReturnEmpty_WhenChannelDoesNotExist() {
        Optional<OneToOneChannelEntity> result = channelRepository.findOneToOneChannel(member1.getId(), 999L);

        assertThat(result).isEmpty();
    }
}
