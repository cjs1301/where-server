//package com.where.server.api.service;
//
//import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketEvent;
//import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketResponse;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.where.server.api.service.channel.ChannelService;
//import com.where.server.api.service.channel.dto.LocationDto;
//import com.where.server.api.service.channel.dto.MessageDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import software.amazon.awssdk.core.SdkBytes;
//import software.amazon.awssdk.services.apigatewaymanagementapi.ApiGatewayManagementApiClient;
//import software.amazon.awssdk.services.apigatewaymanagementapi.model.PostToConnectionRequest;
//
//import java.net.URI;
//import java.util.List;
//
//
//@Component
//public class WebSocketHandler{
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private ChannelService channelService;
//
//    private ApiGatewayManagementApiClient apiGatewayClient;
//
//    public APIGatewayV2WebSocketResponse handleRequest(APIGatewayV2WebSocketEvent event) {
//        initializeApiGatewayClient(event);
//        String connectionId = event.getRequestContext().getConnectionId();
//        String routeKey = event.getRequestContext().getRouteKey();
//
//        try {
//            switch (routeKey) {
//                case "$connect":
//                    return handleConnect(connectionId);
//                case "$disconnect":
//                    return handleDisconnect(connectionId);
//                case "$default":
//                    return handleMessage(connectionId, event.getBody());
//                default:
//                    return createErrorResponse("Unknown route key");
//            }
//        } catch (Exception e) {
//            return createErrorResponse("Error processing request: " + e.getMessage());
//        }
//    }
//
//    private void initializeApiGatewayClient(APIGatewayV2WebSocketEvent event) {
//        String endpoint = "https://" + event.getRequestContext().getDomainName() + "/" + event.getRequestContext().getStage();
//        apiGatewayClient = ApiGatewayManagementApiClient.builder()
//                .endpointOverride(URI.create(endpoint))
//                .build();
//    }
//
//    private APIGatewayV2WebSocketResponse handleConnect(String connectionId) {
//        return createSuccessResponse("Connected");
//    }
//
//    private APIGatewayV2WebSocketResponse handleDisconnect(String connectionId) {
//        channelService.removeConnection(connectionId);
//        return createSuccessResponse("Disconnected");
//    }
//
//    private APIGatewayV2WebSocketResponse handleMessage(String connectionId, String message) throws Exception {
//        JsonNode jsonNode = objectMapper.readTree(message);
//        String destination = jsonNode.get("destination").asText();
//        JsonNode payload = jsonNode.get("payload");
//
//        switch (destination) {
//            case "/pub/join":
//                return handleJoinChannel(connectionId, payload);
//            case "/pub/leave":
//                return handleLeaveChannel(connectionId, payload);
//            case "/topic/chat":
//                return handleSendMessage(connectionId, payload);
//            case "/topic/location":
//                return handleSendLocation(connectionId, payload);
//            default:
//                return createErrorResponse("Unknown destination");
//        }
//    }
//    private APIGatewayV2WebSocketResponse handleJoinChannel(String connectionId, JsonNode payload) throws Exception {
//        String channelId = payload.get("channelId").asText();
//        Long memberId = payload.get("memberId").asLong();
//
//        channelService.addUserToChannel(channelId, memberId, connectionId);
//
//        MessageDto joinMessage = MessageDto.builder()
//                .channelId(channelId)
//                .sender("SYSTEM")
//                .message(memberId + " has joined the channel")
//                .build();
//        channelService.createMessage(joinMessage);
//        broadcastToChannel(channelId, objectMapper.writeValueAsString(joinMessage));
//
//        return createSuccessResponse("Joined channel");
//    }
//
//    private APIGatewayV2WebSocketResponse handleLeaveChannel(String connectionId, JsonNode payload) throws Exception {
//        String channelId = payload.get("channelId").asText();
//        String phoneNumber = payload.get("phoneNumber").asText();
//
//        channelService.removeConnection(connectionId);
//
//        MessageDto leaveMessage = MessageDto.builder()
//                .channelId(channelId)
//                .sender("SYSTEM")
//                .message(phoneNumber + " has left the channel")
//                .build();
//        channelService.createMessage(leaveMessage);
//        broadcastToChannel(channelId, objectMapper.writeValueAsString(leaveMessage));
//
//        return createSuccessResponse("Left channel");
//    }
//
//    private APIGatewayV2WebSocketResponse handleSendMessage(String connectionId, JsonNode payload) throws Exception {
//        String channelId = payload.get("channelId").asText();
//        String sender = payload.get("sender").asText();
//        String content = payload.get("content").asText();
//
//        MessageDto chatMessage = MessageDto.builder()
//                .channelId(channelId)
//                .sender(sender)
//                .message(content)
//                .build();
//        channelService.createMessage(chatMessage);
//        broadcastToChannel(channelId, objectMapper.writeValueAsString(chatMessage));
//
//        return createSuccessResponse("Message sent");
//    }
//
//    private APIGatewayV2WebSocketResponse handleSendLocation(String connectionId, JsonNode payload) throws Exception {
//        LocationDto locationMessage = objectMapper.treeToValue(payload, LocationDto.class);
//        channelService.createLocation(locationMessage);
//        broadcastToChannel(locationMessage.getChannelId(), objectMapper.writeValueAsString(locationMessage));
//
//        return createSuccessResponse("Location sent");
//    }
//
//    private void broadcastToChannel(String channelId, String message) {
//        List<String> connectionIds = channelService.getChannelConnectionIds(channelId);
//        for (String connectionId : connectionIds) {
//            sendMessageToClient(connectionId, message);
//        }
//    }
//
//    private void sendMessageToClient(String connectionId, String message) {
//        try {
//            PostToConnectionRequest postToConnectionRequest = PostToConnectionRequest.builder()
//                    .connectionId(connectionId)
//                    .data(SdkBytes.fromUtf8String(message))
//                    .build();
//
//            apiGatewayClient.postToConnection(postToConnectionRequest);
//        } catch (Exception e) {
//            System.err.println("Failed to send message to connection " + connectionId + ": " + e.getMessage());
//            channelService.removeConnection(connectionId);
//        }
//    }
//
//    private APIGatewayV2WebSocketResponse createSuccessResponse(String body) {
//        APIGatewayV2WebSocketResponse response = new APIGatewayV2WebSocketResponse();
//        response.setStatusCode(200);
//        response.setBody(body);
//        return response;
//    }
//
//    private APIGatewayV2WebSocketResponse createErrorResponse(String errorMessage) {
//        APIGatewayV2WebSocketResponse response = new APIGatewayV2WebSocketResponse();
//        response.setStatusCode(500);
//        response.setBody(errorMessage);
//        return response;
//    }
//}
