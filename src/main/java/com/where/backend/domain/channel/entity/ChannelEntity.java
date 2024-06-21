package com.where.backend.domain.channel.entity;

import com.where.backend.domain.TimeStamped;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="channel")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChannelEntity extends TimeStamped {
//    @Id
//    @UuidGenerator
//    @Column(name = "channel_id", nullable = false)
    @Id
    @Column("channel_id")
    UUID id;
    String name;


//    @OneToMany(mappedBy = "channel")
//    List<FollowChannelEntity> followChannelEntities = new ArrayList<>();
}
