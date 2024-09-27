package org.where.modulewebsocket.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Coordinate;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {
    String type;
    String sender;
    String channelId;
    CoordinateDto coordinates;
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
}
