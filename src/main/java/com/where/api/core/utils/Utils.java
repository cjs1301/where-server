package com.where.api.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.where.api.domain.chating.dto.LocationMessageDto;

public class Utils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Utils(){}

    public static LocationMessageDto getObject(final String socketMessage) throws Exception{
        return objectMapper.readValue(socketMessage, LocationMessageDto.class);
    }
    public static String getString(final LocationMessageDto locationMessageDto) throws Exception{
        return objectMapper.writeValueAsString(locationMessageDto);
    }
}
