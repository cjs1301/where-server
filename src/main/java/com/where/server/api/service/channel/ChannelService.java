package com.where.server.api.service.channel;

import com.where.server.api.service.channel.dto.*;
import com.where.server.domain.channel.LocationEntity;
import com.where.server.domain.channel.MessageEntity;
import com.where.server.domain.channel.ChannelRepository;
import com.where.server.domain.channel.FollowChannelRepository;
import com.where.server.domain.channel.LocationRepository;
import com.where.server.domain.channel.MessageRepository;
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
    private final LocationRepository locationRepository;

    @Transactional
    public FollowChannelDto createChannelAndFollow(CreateChannelDto createChannelDto){
        MemberEntity member = memberRepository.findByPhoneNumber(createChannelDto.getMemberPhoneNumber());
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

    @Transactional
    public void createLocation(LocationDto locationDto){
        MemberEntity member = memberRepository.findByPhoneNumber(locationDto.getSender());
        UUID channelId = UUID.fromString(locationDto.getChannelId());

        LocationEntity locationMessage = LocationEntity.builder()
                .coordinates(locationDto.toCoordinateList())
                .channel(ChannelEntity.builder().id(channelId).build())
                .member(member)
                .build();

        locationRepository.save(locationMessage);
    }

    public void createMessage(MessageDto messageDto){
        MemberEntity member = memberRepository.findByPhoneNumber(messageDto.getSender());
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
