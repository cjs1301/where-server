package org.where.modulecore.domain.channel;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.where.modulecore.domain.member.MemberEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "channel_membership",
        uniqueConstraints = @UniqueConstraint(columnNames = {"channel_id", "member_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChannelMembershipEntity {
    @Id
    @UuidGenerator
    @Column(name = "membership_id")
    private UUID id;

    @Column(name = "connection_id")
    String connectionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private ChannelEntity channel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Builder
    public ChannelMembershipEntity(ChannelEntity channel, MemberEntity member) {
        this.channel = channel;
        this.member = member;
        this.joinedAt = LocalDateTime.now();
    }
    public void updateConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }
    public ChannelType getChannelType() {
        return this.channel.getChannelType();
    }
}

