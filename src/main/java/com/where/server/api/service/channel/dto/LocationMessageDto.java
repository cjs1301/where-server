package com.where.server.api.service.channel.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Coordinate;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationMessageDto {
    String type;
    String sender;
    String channelId;
    List<CoordinateDto> coordinates;
    String sessionId;  // 선택적: 새 경로 세션을 구분하기 위한 ID

    @Getter
    @Builder
    @ToString
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CoordinateDto {
        double latitude;
        double longitude;

        public Coordinate toCoordinate() {
            return new Coordinate(this.longitude, this.latitude);
        }
    }
    public List<Coordinate> toCoordinateList() {
        return this.coordinates.stream()
                .map(CoordinateDto::toCoordinate)
                .collect(Collectors.toList());
    }
}
