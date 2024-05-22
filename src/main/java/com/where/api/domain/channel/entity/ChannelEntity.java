package com.where.api.domain.channel.entity;

import com.where.api.domain.TimeStamped;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @CreatedDate
    @Column("created_at")
    OffsetDateTime createdAt;
    @LastModifiedDate
    @Column("updated_at")
    OffsetDateTime updatedAt;

//    @OneToMany(mappedBy = "channel")
//    List<FollowChannelEntity> followChannelEntities = new ArrayList<>();
}
