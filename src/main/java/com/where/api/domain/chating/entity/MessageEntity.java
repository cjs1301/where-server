package com.where.api.domain.chating.entity;

import com.where.api.core.common.TimeStamped;
import com.where.api.domain.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "message")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String message;
    @ManyToOne
    @JoinColumn(name = "channel_id")
    ChannelEntity channel;
    @ManyToOne
    @JoinColumn(name = "member_id")
    MemberEntity member;
}
