package com.where.api.domain.chating.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CoordinateDto {

    double latitude;
    double longitude;
}
