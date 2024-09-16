package org.where.moduleapi.api.service.channel.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoordinateDto {

    double latitude;
    double longitude;
}
