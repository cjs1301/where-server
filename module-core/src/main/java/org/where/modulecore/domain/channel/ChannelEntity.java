package org.where.modulecore.domain.channel;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import org.where.modulecore.domain.TimeStamped;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "channel")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
