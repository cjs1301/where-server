package com.where.api.service;

import com.where.api.dto.MessageDto;
import com.where.api.domain.LocationMessageEntity;
import com.where.api.domain.MessageEntity;
import com.where.api.repository.ChannelRepository;
import com.where.api.repository.FollowChannelRepository;
import com.where.api.repository.LocationMessageRepository;
import com.where.api.repository.MessageRepository;
import com.where.api.dto.CreateChannelDto;
import com.where.api.dto.FollowChannelDto;
import com.where.api.dto.LocationMessageDto;
import com.where.api.domain.ChannelEntity;
import com.where.api.domain.FollowChannelEntity;
import com.where.api.domain.MemberEntity;
import com.where.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        return FollowChannelDto.fromEntity(followChannelEntity);
    }

    public List<FollowChannelDto> getFollowChannelList(Long memberId){

        return followChannelRepository.findAllByMemberId(memberId).stream().map(FollowChannelDto::fromEntity).toList();
    }
    public List<MessageDto> getChannelMessageList(UUID channelId){
        List<MessageEntity> messageEntities = messageRepository.findAllByChannelId(channelId);
        return messageEntities.stream().map(MessageDto::fromEntity).toList();
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
            messageRepository.deleteAllByChannelId(channelId);
        }
    }
}
