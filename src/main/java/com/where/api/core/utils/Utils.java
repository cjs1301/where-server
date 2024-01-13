package com.where.api.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.where.api.domain.chating.dto.LocationChatMessageDto;

public class Utils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Utils(){}

    public static LocationChatMessageDto getObject(final String socketMessage) throws Exception{
        return objectMapper.readValue(socketMessage, LocationChatMessageDto.class);
    }
    public static String getString(final LocationChatMessageDto locationChatMessageDto) throws Exception{
        return objectMapper.writeValueAsString(locationChatMessageDto);
    }
}
