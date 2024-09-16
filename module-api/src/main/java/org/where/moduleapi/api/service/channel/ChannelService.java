package org.where.moduleapi.api.service.channel;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.where.modulecore.domain.channel.*;
import org.where.modulecore.domain.member.MemberEntity;
import org.where.modulecore.domain.member.MemberRepository;
import org.where.moduleapi.api.service.channel.dto.CreateChannelDto;
import org.where.moduleapi.api.service.channel.dto.FollowChannelDto;
import org.where.moduleapi.api.service.channel.dto.LocationDto;
import org.where.moduleapi.api.service.channel.dto.MessageDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private FollowChannelRepository followChannelRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Transactional
    public FollowChannelDto createChannelAndFollow(CreateChannelDto createChannelDto){
        MemberEntity member = memberRepository.findByPhoneNumber(createChannelDto.getMemberPhoneNumber()).orElseThrow(EntityNotFoundException::new);
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
        MemberEntity member = memberRepository.findByPhoneNumber(locationDto.getSender()).orElseThrow(EntityNotFoundException::new);
        UUID channelId = UUID.fromString(locationDto.getChannelId());

        LocationEntity locationMessage = LocationEntity.builder()
                .coordinates(locationDto.toCoordinateList())
                .channel(ChannelEntity.builder().id(channelId).build())
                .member(member)
                .build();

        locationRepository.save(locationMessage);
    }

    public void createMessage(MessageDto messageDto){
        MemberEntity member = memberRepository.findByPhoneNumber(messageDto.getSender()).orElseThrow(EntityNotFoundException::new);
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

    public void addUserToChannel(String channelId, Long memberId, String connectionId) {
        followChannelRepository.updateConnectionId(UUID.fromString(channelId), memberId, connectionId);
    }

    public List<String> getChannelConnectionIds(String channelId) {
        return followChannelRepository.findActiveConnectionsByChannelId(UUID.fromString(channelId))
                .stream()
                .map(FollowChannelEntity::getConnectionId)
                .collect(Collectors.toList());
    }

    public void removeConnection(String connectionId) {
        followChannelRepository.removeConnectionId(connectionId);
    }
}
