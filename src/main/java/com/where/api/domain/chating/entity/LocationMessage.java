package com.where.api.domain.chating.entity;

import com.where.api.core.common.TimeStamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.geo.Point;

@Entity
@Table(name = "location_message")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationMessage extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id", nullable = false)
    Long id;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point position;
}
