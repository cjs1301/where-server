package org.where.modulecore.channel;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.where.modulecore.QuerydslTestConfig;
import org.where.modulecore.domain.channel.*;
import org.where.modulecore.domain.member.MemberEntity;
import org.where.modulecore.domain.member.MemberRepository;
import org.where.modulecore.domain.member.MemberRole;
import org.where.modulecore.domain.message.MessageEntity;
import org.where.modulecore.domain.message.MessageRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslTestConfig.class)
class ChannelRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ChannelMembershipRepository channelMembershipRepository;
    @Autowired
    private EntityManager entityManager;

    private MemberEntity member1;
    private MemberEntity member2;
    private OneToOneChannelEntity channel;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        channelRepository.deleteAll();
        channelMembershipRepository.deleteAll();

        member1 = createMember("Member 1", "1234567890");
        member2 = createMember("Member 2", "0987654321");


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

        // 메시지 생성
        createMessage(channel, member1, "Message 1 from Member 1", false);
        createMessage(channel, member2, "Message 2 from Member 2", false);
        createMessage(channel, member1, "Message 3 from Member 1", false);
        createMessage(channel, member2, "Message 4 from Member 2", false);
    }
    @Test
    void updateAllToReadExceptMember_ShouldMarkMessagesAsRead() {
        // When
        int updatedCount = messageRepository.updateAllToReadExceptMember(channel.getId(), member1.getId());

        assertThat(updatedCount).isEqualTo(2);

        // 영속성 컨텍스트 초기화
        entityManager.clear();

        // Then
        Sort sortByCreatedAtAsc = Sort.by(Sort.Direction.ASC, "createdAt");
        List<MessageEntity> messages = messageRepository.findAllByChannelId(channel.getId(), sortByCreatedAtAsc);

        assertThat(messages).hasSize(4);

        for (MessageEntity message : messages) {
            if (message.getMember().getId().equals(member1.getId())) {
                assertThat(message.getIsRead()).isFalse();
            } else {
                assertThat(message.getIsRead()).isTrue();
            }
        }
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

    private MemberEntity createMember(String name, String phoneNumber) {
        MemberEntity member = MemberEntity.builder()
                .name(name)
                .isEnabled(true)
                .phoneNumber(phoneNumber)
                .profileImage("profile.jpg")
                .isContactListSynchronized(true)
                .role(MemberRole.ROLE_USER)
                .build();
        return memberRepository.save(member);
    }

    private MessageEntity createMessage(ChannelEntity channel, MemberEntity member, String content, boolean isRead) {
        MessageEntity message = MessageEntity.builder()
                .channel(channel)
                .member(member)
                .message(content)
                .isRead(isRead)
                .build();
        return messageRepository.save(message);
    }
}
