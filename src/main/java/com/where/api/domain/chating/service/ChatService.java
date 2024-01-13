package com.where.api.domain.chating.service;

import com.where.api.domain.chating.repository.ChannelRepository;
import com.where.api.domain.chating.repository.ChatMessageRepository;
import com.where.api.domain.chating.repository.LocationMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChannelRepository channelRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final LocationMessageRepository locationMessageRepository;

    public void createChannel(){

    }
}
