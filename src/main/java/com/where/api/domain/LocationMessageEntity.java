package com.where.api.domain;

import com.where.api.dto.CoordinateDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    ChannelEntity channel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    MemberEntity member;

    public MemberEntity getMember() {
        return member;
    }

    public ChannelEntity getChannel() {
        return channel;
    }

    public static Point createPoint(CoordinateDto coordinateDto){
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(),4326);
        return geometryFactory.createPoint(new Coordinate(coordinateDto.getLatitude(),coordinateDto.getLongitude()));
    }
}
