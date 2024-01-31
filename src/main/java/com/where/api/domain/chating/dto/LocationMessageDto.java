package com.where.api.domain.chating.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationMessageDto {
    String type;
    String sender;
    String channelId;
    CoordinateDto coordinates;
}
