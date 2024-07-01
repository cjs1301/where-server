package com.where.server.api.service.channel.dto;

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
