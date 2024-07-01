package com.where.server.api.service.channel;

import com.where.server.api.service.channel.dto.MessageDto;
import com.where.server.domain.channel.LocationMessageEntity;
import com.where.server.domain.channel.MessageEntity;
import com.where.server.domain.channel.ChannelRepository;
import com.where.server.domain.channel.FollowChannelRepository;
import com.where.server.domain.channel.LocationMessageRepository;
import com.where.server.domain.channel.MessageRepository;
import com.where.server.api.service.channel.dto.CreateChannelDto;
import com.where.server.api.service.channel.dto.FollowChannelDto;
import com.where.server.api.service.channel.dto.LocationMessageDto;
import com.where.server.domain.channel.ChannelEntity;
import com.where.server.domain.channel.FollowChannelEntity;
import com.where.server.domain.member.MemberEntity;
import com.where.server.domain.member.MemberRepository;
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
