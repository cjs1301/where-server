package com.where.api.domain.chating.service;

import com.where.api.domain.chating.entity.ChannelEntity;
import com.where.api.domain.chating.entity.FollowChannelEntity;
import com.where.api.domain.chating.repository.ChannelRepository;
import com.where.api.domain.chating.repository.ChatMessageRepository;
import com.where.api.domain.chating.repository.FollowChannelRepository;
import com.where.api.domain.chating.repository.LocationMessageRepository;
import com.where.api.domain.member.entity.MemberEntity;
import com.where.api.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChannelRepository channelRepository;
    private final FollowChannelRepository followChannelRepository;
    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final LocationMessageRepository locationMessageRepository;

    @Transactional
    public ChannelEntity createChannel(){
        MemberEntity member = memberRepository.findByMobile("010-0000-0000");
        ChannelEntity channel = ChannelEntity.builder()
                .name("채널 이름")
                .build();
        channelRepository.save(channel);
        FollowChannelEntity followChannelEntity = FollowChannelEntity.builder()
                .channel(channel)
                .member(member)
                .build();
        followChannelRepository.save(followChannelEntity);
        return channel;
    }
}
