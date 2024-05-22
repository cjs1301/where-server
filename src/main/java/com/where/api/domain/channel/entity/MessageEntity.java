package com.where.api.domain.channel.entity;

import com.where.api.domain.TimeStamped;
import com.where.api.domain.channel.entity.ChannelEntity;
import com.where.api.domain.member.MemberEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "message")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
