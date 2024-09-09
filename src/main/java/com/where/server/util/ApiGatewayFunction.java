package com.where.server.util;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.Message;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ApiGatewayFunction {

    public static <T> Function<Message<T>, Message<?>> create(
            ObjectMapper objectMapper,
            BiFunction<Message<T>, APIGatewayProxyRequestEvent, Message<?>> callable
    ) {
        return input -> {
            try {
                APIGatewayProxyRequestEvent context = objectMapper.readValue(
                        objectMapper.writeValueAsString(input.getHeaders()),
                        APIGatewayProxyRequestEvent.class
                );

                return callable.apply(input, context);
            } catch (Throwable e) {
                String message = e.getMessage();
                if (message != null) {
                    message = message.replace("\n", "").replace("\"", "'");
                }
                return ResponseBuilder.buildJsonErrorResponse(message != null ? message : "", 500);
            }
        };
    }
}
