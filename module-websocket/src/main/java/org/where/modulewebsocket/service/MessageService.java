package org.where.modulewebsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.where.modulecore.domain.channel.FollowChannelEntity;
import org.where.modulecore.domain.channel.FollowChannelRepository;
import org.where.modulewebsocket.service.dto.LocationDto;
import org.where.modulewebsocket.service.dto.MessageDto;

@Service
public class MessageService {
    private final FollowChannelRepository followChannelRepository;
    private final ObjectMapper objectMapper;

    public MessageService(FollowChannelRepository followChannelRepository, ObjectMapper objectMapper) {
        this.followChannelRepository = followChannelRepository;
        this.objectMapper = objectMapper;
    }

    public void routeMessage(String connectionId, String messageBody) throws Exception {
        FollowChannelEntity followChannel = followChannelRepository.findByConnectionId(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection not found"));

        // messageBody를 파싱하여 적절한 DTO로 변환
        // 예: MessageDto 또는 LocationDto

        if (messageBody.contains("type")) {
            LocationDto locationDto = objectMapper.readValue(messageBody, LocationDto.class);
            handleLocation(followChannel, locationDto);
        } else {
            MessageDto messageDto = objectMapper.readValue(messageBody, MessageDto.class);
            handleChat(followChannel, messageDto);
        }
    }

    private void handleChat(FollowChannelEntity followChannel, MessageDto messageDto) {
        // 채팅 메시지 처리 로직
        // 예: 메시지를 저장하고 채널의 다른 구독자들에게 브로드캐스트
    }

    private void handleLocation(FollowChannelEntity followChannel, LocationDto locationDto) {
        // 위치 정보 처리 로직
        // 예: 위치 정보를 저장하고 채널의 다른 구독자들에게 브로드캐스트
    }
}
