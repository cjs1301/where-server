package com.where.api.domain.chating.entity;

import com.where.api.core.common.TimeStamped;
import com.where.api.domain.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.geo.Point;

@Entity
@Table(name = "location_message")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationMessageEntity extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_message_id", nullable = false)
    Long id;

    @Column(columnDefinition = "geometry(Point, 4326)")
    Point position;
    @ManyToOne
    @JoinColumn(name = "channel_id")
    ChannelEntity channel;
    @ManyToOne
    @JoinColumn(name = "member_id")
    MemberEntity member;
}
