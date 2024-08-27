package com.where.server.domain.channel;

import com.where.server.domain.TimeStamped;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "channel")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChannelEntity extends TimeStamped {
    @Id
    @UuidGenerator
    @Column(name = "channel_id", nullable = false)
    UUID id;
    String name;

    @OneToMany(mappedBy = "channel")
    @Builder.Default
    List<FollowChannelEntity> followChannelEntities = new ArrayList<>();
}
