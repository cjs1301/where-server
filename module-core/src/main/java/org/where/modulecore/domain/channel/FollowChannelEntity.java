package org.where.modulecore.domain.channel;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import org.where.modulecore.domain.member.MemberEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "follow_channel",uniqueConstraints = {
        @UniqueConstraint(name = "uk_channel_member", columnNames = {"channel_id", "member_id"})
})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FollowChannelEntity {
    @Id
    @UuidGenerator
    @Column(name = "follow_channel_id", nullable = false)
    UUID id;

    @Column(name = "connection_id")
    String connectionId;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    ChannelEntity channel;
    @ManyToOne
    @JoinColumn(name = "member_id")
    MemberEntity member;

    @Version
    private Long version;

    public void updateConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }
}
