package org.where.modulewebsocket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.where.modulecore.domain.channel.FollowChannelEntity;
import org.where.modulecore.domain.channel.FollowChannelRepository;

import java.util.UUID;

@Service
public class WebSocketConnectionService {

    private final FollowChannelRepository followChannelRepository;

    public WebSocketConnectionService(FollowChannelRepository followChannelRepository) {
        this.followChannelRepository = followChannelRepository;
    }
    @Transactional
    public void handleConnect(String connectionId, Long memberId, UUID channelId) {
        FollowChannelEntity followChannel = followChannelRepository
                .findByChannelIdAndMemberId(channelId, memberId)
                .orElseThrow(() -> new RuntimeException("Follow relationship not found"));
        followChannel.updateConnectionId(connectionId);
        followChannelRepository.save(followChannel);
    }

    @Transactional
    public void handleDisconnect(String connectionId) {
        FollowChannelEntity followChannel = followChannelRepository
                .findByConnectionId(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection not found"));
        followChannel.updateConnectionId(null);
        followChannelRepository.save(followChannel);
    }

    public boolean isConnected(Long memberId, UUID channelId) {
        return followChannelRepository
                .findByChannelIdAndMemberId(channelId, memberId)
                .map(followChannel -> followChannel.getConnectionId() != null)
                .orElse(false);
    }

//    private APIGatewayV2WebSocketResponse createResponse(int statusCode, String body) {
//        APIGatewayV2WebSocketResponse response = new APIGatewayV2WebSocketResponse();
//        response.setStatusCode(statusCode);
//        response.setBody(body);
//        return response;
//    }



//    public void createMessage(MessageDto messageDto){
//        MemberEntity member = memberRepository.findByPhoneNumber(messageDto.getSender()).orElseThrow(EntityNotFoundException::new);
//        MessageEntity message = MessageEntity.builder()
//                .message(messageDto.getMessage())
//                .member(member)
//                .channel(ChannelEntity.builder().id(UUID.fromString(messageDto.getChannelId())).build())
//                .build();
//        messageRepository.save(message);
//    }
}
