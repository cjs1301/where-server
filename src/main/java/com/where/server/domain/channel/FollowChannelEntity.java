package com.where.server.domain.channel;

import com.where.server.domain.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "channel_id")
    ChannelEntity channel;
    @ManyToOne
    @JoinColumn(name = "member_id")
    MemberEntity member;
}
