package com.where.api.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.where.api.domain.socket_test.dto.SocketMessage;

public class Utils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Utils(){}

    public static SocketMessage getObject(final String socketMessage) throws Exception{
        return objectMapper.readValue(socketMessage, SocketMessage.class);
    }
    public static String getString(final SocketMessage socketMessage) throws Exception{
        return objectMapper.writeValueAsString(socketMessage);
    }
}
