package com.where.server.util;


import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class ResponseBuilder {

    public record ResponseError(String message) {}

    public static <T> Message<T> buildJsonResponse(T data) {
        return buildJsonResponse(data, 200);
    }

    public static <T> Message<T> buildJsonResponse(T data, int code) {
        return MessageBuilder
                .withPayload(data)
                .setHeader("Content-Type", "application/json")
                .setHeader("Access-Control-Allow-Origin", "*")
                .setHeader("Access-Control-Allow-Methods", "OPTIONS,POST,GET")
                .setHeader("statusCode", code)
                .build();
    }

    public static Message<ResponseError> buildJsonErrorResponse(String message) {
        return buildJsonErrorResponse(message, 500);
    }

    public static Message<ResponseError> buildJsonErrorResponse(String message, int code) {
        return buildJsonResponse(new ResponseError(message), code);
    }
}
