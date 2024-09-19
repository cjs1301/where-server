package org.where.modulewebsocket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.where.modulecore.domain.channel.ChannelMembershipEntity;
import org.where.modulecore.domain.channel.ChannelMembershipRepository;

import java.util.UUID;

@Service
public class WebSocketConnectionService {

    private final ChannelMembershipRepository channelMembershipRepository;

    public WebSocketConnectionService(ChannelMembershipRepository channelMembershipRepository) {
        this.channelMembershipRepository = channelMembershipRepository;
    }
    @Transactional
    public void handleConnect(String connectionId, Long memberId, UUID channelId) {
        channelMembershipRepository
                .updateConnectionId(channelId, memberId, connectionId);

    }

    @Transactional
    public void handleDisconnect(String connectionId) {
        ChannelMembershipEntity followChannel = channelMembershipRepository
                .findByConnectionId(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection not found"));
        followChannel.updateConnectionId(null);
        channelMembershipRepository.save(followChannel);
    }

    public boolean isConnected(Long memberId, UUID channelId) {
        return channelMembershipRepository
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
