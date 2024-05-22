package com.where.api.domain.channel.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "follow_channel",uniqueConstraints = {
//        @UniqueConstraint(name = "uk_channel_member", columnNames = {"channel_id", "member_id"})
//})
@Table(name = "follow_channel")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FollowChannelEntity {
//    @Id
//    @UuidGenerator
//    @Column(name = "follow_channel_id", nullable = false)
    @Id
    UUID id;

    @Column("channel_id")
    String channelId;
    @Column("member_id")
    String memberId;
}
