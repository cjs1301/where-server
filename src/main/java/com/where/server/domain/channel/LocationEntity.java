package com.where.server.domain.channel;

import com.where.server.domain.TimeStamped;
import com.where.server.domain.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.*;

import java.util.List;

@Entity
@Table(name = "location")
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationEntity extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false)
    Long id;

    @Column(columnDefinition = "geometry(LineString, 4326)")
    LineString route;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    ChannelEntity channel;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    MemberEntity member;

    @Builder
    public LocationEntity(Long id, List<Coordinate> coordinates, ChannelEntity channel, MemberEntity member) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        this.id = id;
        this.route = geometryFactory.createLineString(coordinates.toArray(new Coordinate[0]));
        this.channel = channel;
        this.member = member;
    }
    // 경로에 새로운 좌표 추가
    public void addCoordinate(double latitude, double longitude) {
        Coordinate[] existingCoords = route.getCoordinates();
        Coordinate[] newCoords = new Coordinate[existingCoords.length + 1];
        System.arraycopy(existingCoords, 0, newCoords, 0, existingCoords.length);
        newCoords[existingCoords.length] = new Coordinate(longitude, latitude);

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        this.route = geometryFactory.createLineString(newCoords);
    }

    // 전체 경로 가져오기
    public Coordinate[] getRouteCoordinates() {
        return route.getCoordinates();
    }
}
