package org.where.modulewebsocket;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.where.modulewebsocket.service.MessageService;
import org.where.modulewebsocket.service.WebSocketConnectionService;


import java.util.UUID;
import java.util.function.Function;

@Slf4j
@Component
public class WebSocketLambdaHandler implements Function<APIGatewayV2WebSocketEvent, APIGatewayV2WebSocketResponse> {

    private final WebSocketConnectionService connectionService;
    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    public WebSocketLambdaHandler(WebSocketConnectionService connectionService, MessageService messageService, ObjectMapper objectMapper) {
        this.connectionService = connectionService;
        this.messageService = messageService;
        this.objectMapper = objectMapper;
    }

    @Override
    public APIGatewayV2WebSocketResponse apply(APIGatewayV2WebSocketEvent event) {
        try {
            String connectionId = event.getRequestContext().getConnectionId();
            String routeKey = event.getRequestContext().getRouteKey();
            log.info("Received event. RouteKey: {}, ConnectionId: {}", routeKey, connectionId);

            return switch (routeKey) {
                case "$connect" -> handleConnect(connectionId);
                case "$disconnect" -> handleDisconnect(connectionId);
                case "$default" -> handleDefaultMessage(connectionId, event.getBody());
                default -> createResponse(400, "Unsupported route: " + routeKey);
            };
        } catch (Exception e) {
            log.error("Error processing request", e);
            return createResponse(500, "Internal server error: " + e.getMessage());
        }
    }

    private APIGatewayV2WebSocketResponse handleConnect(String connectionId) {
        try {
            log.info("Handling Connect. ConnectionId: {}", connectionId);
            return createResponse(200, "Connected.");
        } catch (Exception e) {
            log.error("Error handling connect", e);
            return createResponse(500, "Error connecting: " + e.getMessage());
        }
    }

    private APIGatewayV2WebSocketResponse handleDisconnect(String connectionId) {
        try {
            log.info("Disconnecting. ConnectionId: {}", connectionId);
            connectionService.handleDisconnect(connectionId);
            return createResponse(200, "Disconnected.");
        } catch (Exception e) {
            log.error("Error handling disconnect", e);
            return createResponse(500, "Error disconnecting: " + e.getMessage());
        }
    }

    private APIGatewayV2WebSocketResponse handleDefaultMessage(String connectionId, String body) {
        try {
            log.info("Handling default message. ConnectionId: {}, Body: {}", connectionId, body);
            JsonNode jsonNode = objectMapper.readTree(body);
            String action = jsonNode.get("action").asText();
            String data = jsonNode.get("data").toString();

            return switch (action) {
                case "subscribe" -> handleSubscribe(connectionId, data);
                case "chat" -> handleChatMessage(connectionId, data);
                case "location" -> handleLocationMessage(connectionId, data);
                default -> createResponse(400, "Unsupported action: " + action);
            };
        } catch (Exception e) {
            log.error("Error handling default message", e);
            return createResponse(500, "Error processing message: " + e.getMessage());
        }
    }

    private APIGatewayV2WebSocketResponse handleSubscribe(String connectionId, String body) {
        try {
            log.info("Handling subscribe. ConnectionId: {}, Body: {}", connectionId, body);
            JsonNode jsonNode = objectMapper.readTree(body);
            Long memberId = jsonNode.get("memberId").asLong();
            UUID channelId = UUID.fromString(jsonNode.get("channelId").asText());

            connectionService.handleSubscribe(connectionId, memberId, channelId);
            return createResponse(200, "Subscribed to channel.");
        } catch (Exception e) {
            log.error("Error handling subscribe", e);
            return createResponse(500, "Error subscribing: " + e.getMessage());
        }
    }

    private APIGatewayV2WebSocketResponse handleChatMessage(String connectionId, String body) {
        try {
            log.info("Handling chat message. ConnectionId: {}, Body: {}", connectionId, body);
            messageService.handleChat(connectionId, body);
            return createResponse(200, "Chat message processed.");
        } catch (Exception e) {
            log.error("Error handling chat message", e);
            return createResponse(500, "Error processing chat message: " + e.getMessage());
        }
    }

    private APIGatewayV2WebSocketResponse handleLocationMessage(String connectionId, String body) {
        try {
            log.info("Handling location message. ConnectionId: {}, Body: {}", connectionId, body);
            messageService.handleLocation(connectionId, body);
            return createResponse(200, "Location update processed.");
        } catch (Exception e) {
            log.error("Error handling location message", e);
            return createResponse(500, "Error processing location update: " + e.getMessage());
        }
    }

    private APIGatewayV2WebSocketResponse createResponse(int statusCode, String body) {
        APIGatewayV2WebSocketResponse response = new APIGatewayV2WebSocketResponse();
        response.setStatusCode(statusCode);
        response.setBody(body);
        return response;
    }
}
