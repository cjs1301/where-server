package com.where.backend.domain.channel.entity;

import com.where.backend.domain.TimeStamped;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

//@Entity
@Table(name = "message")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageEntity extends TimeStamped {
//    @Id
//    @UuidGenerator
//    @Column(name = "message_id", nullable = false)
//    UUID id;
//    String message;
//    @ManyToOne(cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "channel_id")
//    ChannelEntity channel;
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    MemberEntity member;

    @Id
    @Column("message_id")
    UUID id;
    String message;
    @Column("channel_id")
    UUID channelId;
    @Column("member_id")
    Long memberId;
}
