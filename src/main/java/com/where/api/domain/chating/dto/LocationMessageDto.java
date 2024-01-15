package com.where.api.domain.chating.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocationMessageDto {
    private String type;
    private String sender;
    private String channelId;
    private CoordinateDto coordinates;
}
