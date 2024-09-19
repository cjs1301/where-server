package org.where.modulewebsocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.where.modulecore.domain.channel.ChannelMembershipEntity;
import org.where.modulecore.domain.channel.ChannelMembershipRepository;
import org.where.modulecore.domain.message.MessageEntity;
import org.where.modulecore.domain.message.MessageRepository;
import org.where.modulewebsocket.service.dto.BroadcastMessage;
import org.where.modulewebsocket.service.dto.LocationDto;
import org.where.modulewebsocket.service.dto.MessageDto;
import org.where.modulewebsocket.service.dto.SocketMessageDto;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.apigatewaymanagementapi.ApiGatewayManagementApiClient;
import software.amazon.awssdk.services.apigatewaymanagementapi.model.ApiGatewayManagementApiException;
import software.amazon.awssdk.services.apigatewaymanagementapi.model.GoneException;
import software.amazon.awssdk.services.apigatewaymanagementapi.model.PostToConnectionRequest;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MessageService {
    private final ChannelMembershipRepository channelMembershipRepository;
    private final MessageRepository messageRepository;
    private final ApiGatewayManagementApiClient apiGatewayManagementApiClient;
    private final ObjectMapper objectMapper;

    public MessageService(ChannelMembershipRepository channelMembershipRepository,
                          ApiGatewayManagementApiClient apiGatewayManagementApiClient,
                          MessageRepository messageRepository,
                          ObjectMapper objectMapper) {
        this.messageRepository = messageRepository;
        this.channelMembershipRepository = channelMembershipRepository;
        this.apiGatewayManagementApiClient = apiGatewayManagementApiClient;
        this.objectMapper = objectMapper;
    }
    public void handleChat(String connectionId, String messageBody) throws JsonProcessingException {
        log.info("Handling chat message. ConnectionId: {}, MessageBody: {}", connectionId, messageBody);
        ChannelMembershipEntity followChannel = channelMembershipRepository.findByConnectionId(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection not found for connectionId: " + connectionId));

        SocketMessageDto socketMessageDto = objectMapper.readValue(messageBody, SocketMessageDto.class);

        MessageEntity messageEntity = MessageEntity.builder()
                .channel(followChannel.getChannel())
                .message(socketMessageDto.getMessage())
                .member(followChannel.getMember())
                .build();
        messageRepository.save(messageEntity);
        MessageDto messageDto = MessageDto.fromEntity(messageEntity);

        log.info("Broadcasting chat message to channel. ChannelId: {}", followChannel.getChannel().getId());
        broadcastToChannel(followChannel.getChannel().getId(), "chat", messageDto);

        log.info("Sending confirmation to sender. ConnectionId: {}", connectionId);
        sendMessageToConnection(connectionId, "Message sent successfully");
    }

    public void handleLocation(String connectionId, String messageBody) throws JsonProcessingException {
        log.info("Handling location update. ConnectionId: {}, MessageBody: {}", connectionId, messageBody);
        ChannelMembershipEntity followChannel = channelMembershipRepository.findByConnectionId(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection not found for connectionId: " + connectionId));
        LocationDto locationDto = objectMapper.readValue(messageBody, LocationDto.class);

        log.info("Broadcasting location update to channel. ChannelId: {}", followChannel.getChannel().getId());
        broadcastToChannel(followChannel.getChannel().getId(), "location", locationDto);

        log.info("Sending confirmation to sender. ConnectionId: {}", connectionId);
        sendMessageToConnection(connectionId, "Location update received");
    }

    private void broadcastToChannel(UUID channelId, String type, Object data) throws JsonProcessingException {
        List<ChannelMembershipEntity> subscribers = channelMembershipRepository.findAllByChannelId(channelId);
        String message = objectMapper.writeValueAsString(new BroadcastMessage(type, data));

        log.info("Broadcasting to channel. ChannelId: {}, Subscribers count: {}", channelId, subscribers.size());
        for (ChannelMembershipEntity subscriber : subscribers) {
            if (subscriber.getConnectionId() != null) {
                sendMessageToConnection(subscriber.getConnectionId(), message);
            }
        }
    }

    private void sendMessageToConnection(String connectionId, String message) {
        try {
            log.info("Sending message to connection. ConnectionId: {}, Message: {}", connectionId, message);
            PostToConnectionRequest request = PostToConnectionRequest.builder()
                    .connectionId(connectionId)
                    .data(SdkBytes.fromUtf8String(message))
                    .build();

            apiGatewayManagementApiClient.postToConnection(request);
        } catch (GoneException e) {
            log.warn("Connection gone. Handling disconnected client. ConnectionId: {}", connectionId);
            handleDisconnectedClient(connectionId);
        } catch (ApiGatewayManagementApiException e) {
            log.error("API Gateway error. StatusCode: {}, ErrorMessage: {}, ErrorCode: {}, RequestId: {}",
                    e.statusCode(), e.getMessage(), e.awsErrorDetails().errorCode(), e.requestId());
            // 여기에 추가적인 에러 처리 로직을 구현할 수 있습니다.
        } catch (Exception e) {
            log.error("Unexpected error sending message to connection. ConnectionId: {}", connectionId, e);
        }
    }

    private void handleDisconnectedClient(String connectionId) {
        channelMembershipRepository.removeConnectionId(connectionId);
    }
}
