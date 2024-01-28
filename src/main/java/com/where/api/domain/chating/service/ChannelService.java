package com.where.api.domain.chating.service;

import com.where.api.domain.chating.dto.CreateChannelDto;
import com.where.api.domain.chating.dto.LocationMessageDto;
import com.where.api.domain.chating.dto.MessageDto;
import com.where.api.domain.chating.entity.ChannelEntity;
import com.where.api.domain.chating.entity.FollowChannelEntity;
import com.where.api.domain.chating.entity.LocationMessageEntity;
import com.where.api.domain.chating.entity.MessageEntity;
import com.where.api.domain.chating.repository.ChannelRepository;
import com.where.api.domain.chating.repository.MessageRepository;
import com.where.api.domain.chating.repository.FollowChannelRepository;
import com.where.api.domain.chating.repository.LocationMessageRepository;
import com.where.api.domain.member.entity.MemberEntity;
import com.where.api.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final FollowChannelRepository followChannelRepository;
    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;
    private final LocationMessageRepository locationMessageRepository;

    @Transactional
    public ChannelEntity createChannel(CreateChannelDto createChannelDto){
        MemberEntity member = memberRepository.findByMobile(createChannelDto.getMemberMobileNumber());
        ChannelEntity channel = ChannelEntity.builder()
                .name(createChannelDto.getChannelName())
                .build();
        channelRepository.save(channel);
        FollowChannelEntity followChannelEntity = FollowChannelEntity.builder()
                .channel(channel)
                .member(member)
                .build();
        followChannelRepository.save(followChannelEntity);
        return channel;
    }

    public List<ChannelEntity> getMemberChannelList(Long memberId){
        return followChannelRepository.findAllByMemberId(memberId).orElse(new ArrayList<>());
    }
    public List<MessageDto> getChannelMessageList(UUID channelId){
        Optional<List<MessageEntity>> messageEntities = messageRepository.findAllByChannelId(channelId);
        return messageEntities.map(entities -> entities.stream().map(MessageDto::fromEntity).toList()).orElseGet(ArrayList::new);
    }

    public void createLocationMessage(LocationMessageDto messageDto){
        MemberEntity member = memberRepository.findByMobile(messageDto.getSender());
        LocationMessageEntity locationMessage = LocationMessageEntity.builder()
                .position(new Point(messageDto.getCoordinates().getLatitude(),messageDto.getCoordinates().getLongitude()))
                .member(member)
                .channel(ChannelEntity.builder().id(UUID.fromString(messageDto.getChannelId())).build())
                .build();
        locationMessageRepository.save(locationMessage);
    }

    public void createMessage(MessageDto messageDto){
        MemberEntity member = memberRepository.findByMobile(messageDto.getSender());
        MessageEntity message = MessageEntity.builder()
                .message(messageDto.getMessage())
                .member(member)
                .channel(ChannelEntity.builder().id(UUID.fromString(messageDto.getChannelId())).build())
                .build();
        messageRepository.save(message);
    }
}
