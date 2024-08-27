package com.where.server.domain.channel;

import com.where.server.domain.TimeStamped;
import com.where.server.domain.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

@Entity
@Table(name = "location_message")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationMessageEntity extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_message_id", nullable = false)
    Long id;

    @Column(columnDefinition = "geometry(Point, 4326)")
    Point position;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    ChannelEntity channel;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    MemberEntity member;

    @Builder
    public LocationMessageEntity(Long id, double latitude, double longitude, ChannelEntity channel, MemberEntity member) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        this.id = id;
        this.position = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        this.channel = channel;
        this.member = member;
    }
}
