package com.where.api.domain.chating.entity;

import com.where.api.core.common.TimeStamped;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.UuidGenerator;

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
}
