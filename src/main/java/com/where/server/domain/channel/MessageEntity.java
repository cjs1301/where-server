package com.where.server.domain.channel;

import com.where.server.domain.TimeStamped;
import com.where.server.domain.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "message")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageEntity extends TimeStamped {
    @Id
    @UuidGenerator
    @Column(name = "message_id", nullable = false)
    UUID id;
    String message;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "channel_id")
    ChannelEntity channel;
    @ManyToOne
    @JoinColumn(name = "member_id")
    MemberEntity member;
}
