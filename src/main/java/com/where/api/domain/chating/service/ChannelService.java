package com.where.api.domain.chating.service;

import com.where.api.domain.chating.dto.CreateChannelDto;
import com.where.api.domain.chating.dto.FollowChannelDto;
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
    public FollowChannelDto createChannelAndFollow(CreateChannelDto createChannelDto){
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
        log.info(followChannelEntity.toString());
        return FollowChannelDto.fromEntity(followChannelEntity);
    }

    public List<FollowChannelDto> getFollowChannelList(Long memberId){

        return followChannelRepository.findAllByMemberId(memberId).stream().map(FollowChannelDto::fromEntity).toList();
    }
    public List<MessageDto> getChannelMessageList(UUID channelId){
        Optional<List<MessageEntity>> messageEntities = messageRepository.findAllByChannelId(channelId);
        return messageEntities.map(entities -> entities.stream().map(MessageDto::fromEntity).toList()).orElseGet(ArrayList::new);
    }

    public void createLocationMessage(LocationMessageDto messageDto){
        MemberEntity member = memberRepository.findByMobile(messageDto.getSender());
        LocationMessageEntity locationMessage = LocationMessageEntity.builder()
                .position(LocationMessageEntity.createPoint(messageDto.getCoordinates()))
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


    public FollowChannelDto createFollowChannel(UUID channelId, Long memberId) {
        ChannelEntity channel = channelRepository.findById(channelId).orElseThrow();
        MemberEntity member = memberRepository.findById(memberId).orElseThrow();
        Boolean isExists = followChannelRepository.existsByChannelIdAndMemberId(channelId,memberId);
        if(Boolean.TRUE.equals(isExists)){
            return FollowChannelDto.fromEntity(followChannelRepository.findByChannelIdAndMemberId(channelId,memberId));
        }
        FollowChannelEntity followChannelEntity = FollowChannelEntity.builder().member(member).channel(channel).build();
        followChannelRepository.save(followChannelEntity);
        return FollowChannelDto.fromEntity(followChannelEntity);
    }

    public void deleteFollowChannel(UUID channelId,UUID followChannelId) {
        followChannelRepository.deleteById(followChannelId);
        ChannelEntity channel = channelRepository.findById(channelId).orElseThrow();
        if(channel.getFollowChannelEntities().isEmpty()){
            log.info("channelRepository.deleteById({})",channelId);
            messageRepository.deleteAllByChannelId(channelId);
        }
    }
}
