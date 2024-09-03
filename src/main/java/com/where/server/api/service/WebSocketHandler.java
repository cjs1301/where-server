package com.where.server.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.where.server.api.service.channel.ChannelService;
import com.where.server.api.service.channel.dto.LocationDto;
import com.where.server.api.service.channel.dto.MessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper;
    private final ChannelService channelService;
    private final RabbitTemplate rabbitTemplate;

    public WebSocketHandler(ObjectMapper objectMapper, ChannelService channelService, RabbitTemplate rabbitTemplate) {
        this.objectMapper = objectMapper;
        this.channelService = channelService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        SESSIONS.put(session.getId(), session);
        // 연결 시 입장 메시지를 보내지 않고, 클라이언트가 채널 입장 메시지를 보낼 때까지 기다립니다.
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        JsonNode jsonNode = objectMapper.readTree(payload);
        String type = jsonNode.get("type").asText();
        String channelId = jsonNode.get("channelId").asText();
        String sender = jsonNode.get("sender").asText();

        switch (type) {
            case "JOIN":
                handleJoinMessage(channelId, sender);
                break;
            case "LEAVE":
                handleLeaveMessage(channelId, sender);
                break;
            case "LOCATION":
                handleLocationMessage(jsonNode, channelId, payload);
                break;
            case "CHAT":
                handleChatMessage(jsonNode, channelId, payload);
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        SESSIONS.remove(session.getId());
        // 연결 종료 시 퇴장 메시지를 보내지 않습니다. 클라이언트가 명시적으로 채널 퇴장 메시지를 보내야 합니다.
    }

    private void handleJoinMessage(String channelId, String sender) throws Exception {
        MessageDto joinMessage = MessageDto.builder()
                .channelId(channelId)
                .sender("SYSTEM")
                .message(sender + " has joined the channel")
                .build();
        channelService.createMessage(joinMessage);
        rabbitTemplate.convertAndSend("chat.channels." + channelId, objectMapper.writeValueAsString(joinMessage));
    }

    private void handleLeaveMessage(String channelId, String sender) throws Exception {
        MessageDto leaveMessage = MessageDto.builder()
                .channelId(channelId)
                .sender("SYSTEM")
                .message(sender + " has left the channel")
                .build();
        channelService.createMessage(leaveMessage);
        rabbitTemplate.convertAndSend("chat.channels." + channelId, objectMapper.writeValueAsString(leaveMessage));
    }

    private void handleLocationMessage(JsonNode jsonNode, String channelId, String payload) throws Exception {
        LocationDto locationMessage = objectMapper.treeToValue(jsonNode, LocationDto.class);
        channelService.createLocation(locationMessage);
        rabbitTemplate.convertAndSend("location.channels." + channelId, payload);
    }

    private void handleChatMessage(JsonNode jsonNode, String channelId, String payload) throws Exception {
        MessageDto chatMessage = MessageDto.builder()
                .channelId(channelId)
                .sender(jsonNode.get("sender").asText())
                .message(jsonNode.get("message").asText())
                .build();
        channelService.createMessage(chatMessage);
        rabbitTemplate.convertAndSend("chat.channels." + channelId, payload);
    }
}
