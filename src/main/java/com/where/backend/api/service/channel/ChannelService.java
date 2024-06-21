package com.where.backend.api.service.channel;

import com.where.backend.domain.channel.entity.ChannelEntity;
import com.where.backend.domain.channel.entity.ChatMessageEntity;
import com.where.backend.domain.channel.entity.FollowChannelEntity;
import com.where.backend.domain.channel.repository.ChannelRepository;
import com.where.backend.domain.channel.repository.FollowChannelRepository;
import com.where.backend.domain.channel.repository.LocationMessageRepository;
import com.where.backend.domain.channel.repository.ChatMessageRepository;
import com.where.backend.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final FollowChannelRepository followChannelRepository;
    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final LocationMessageRepository locationMessageRepository;


    private Mono<ChannelEntity> saveChannelEntity(ChannelEntity channel){
        return channelRepository.save(channel);
    }


    public Mono<ChannelEntity> createChannel(ChannelEntity channel){
       return saveChannelEntity(channel);
    }
    public Mono<Void> deleteChannel(UUID channelId){
        return channelRepository.existsById(channelId)
                .flatMap(isExists ->{
                    if(isExists){
                        return channelRepository.deleteById(channelId);
                    } else {
                        return Mono.error(new IllegalArgumentException("채널 아이디 찾을 수 없음"));
                    }
                });
    }

    public Mono<Void> deleteFollowChannel(UUID followId){
        return followChannelRepository.existsById(followId)
                .flatMap(isExists ->{
                    if(isExists){
                        return followChannelRepository.deleteById(followId);
                    } else {
                        return Mono.error(new IllegalArgumentException("following 하고 있는 채널 아이디 찾을 수 없음"));
                    }
                });
    }

    public Mono<List<ChannelEntity>> findFollowChannelList(Long memberId){
        return followChannelRepository.findAllChannelsByMemberId(memberId);
    }


    public Mono<List<ChatMessageEntity>> findChannelMessageList(UUID channelId) {
        return chatMessageRepository.findAllByChannelId(channelId);
    }

    public Mono<FollowChannelEntity> createFollowChannel(UUID channelId, Long memberId) {
        return followChannelRepository.existsByChannelIdAndMemberId(channelId,memberId).flatMap(isExists ->{
            if(isExists){
                return followChannelRepository.findByChannelIdAndMemberId(channelId,memberId);
            } else {
                return channelRepository.existsById(channelId)
                        .flatMap(isExistChannel ->{
                            if(isExistChannel){
                                FollowChannelEntity newFollowChannel = FollowChannelEntity.builder()
                                        .id(UUID.randomUUID())
                                        .channelId(channelId)
                                        .memberId(memberId)
                                        .build();
                                return followChannelRepository.save(newFollowChannel);
                            } else {
                                return Mono.error(new IllegalArgumentException("채널 아이디 찾을 수 없음"));
                            }
                        });
            }
        });
    }
}
/*    @Transactional
    public FollowChannelDto createChannelAndFollow(ChannelDto channelDto){
        MemberEntity member = memberRepository.findByMobile(channelDto.getMemberMobileNumber());
        ChannelEntity channel = ChannelEntity.builder()
                .name(channelDto.getChannelName())
                .build();
        channelRepository.save(channel);
        FollowChannelEntity followChannelEntity = FollowChannelEntity.builder()
                .channel(channel)
                .member(member)
                .build();
        followChannelRepository.save(followChannelEntity);
        return FollowChannelDto.fromEntity(followChannelEntity);
    }*/
//public List<FollowChannelDto> getFollowChannelList(Long memberId){
//
//    return followChannelRepository.findAllByMemberId(memberId).stream().map(FollowChannelDto::fromEntity).toList();
//}
//public List<MessageDto> getChannelMessageList(UUID channelId){
//    List<MessageEntity> messageEntities = messageRepository.findAllByChannelId(channelId);
//    return messageEntities.stream().map(MessageDto::fromEntity).toList();
//}
//
//public void createLocationMessage(LocationMessageDto messageDto){
//    MemberEntity member = memberRepository.findByMobile(messageDto.getSender());
//    LocationMessageEntity locationMessage = LocationMessageEntity.builder()
//            .position(LocationMessageEntity.createPoint(messageDto.getCoordinates()))
//            .member(member)
//            .channel(ChannelEntity.builder().id(UUID.fromString(messageDto.getChannelId())).build())
//            .build();
//    locationMessageRepository.save(locationMessage);
//}
//
//public void createMessage(MessageDto messageDto){
//    MemberEntity member = memberRepository.findByMobile(messageDto.getSender());
//    MessageEntity message = MessageEntity.builder()
//            .message(messageDto.getMessage())
//            .member(member)
//            .channel(ChannelEntity.builder().id(UUID.fromString(messageDto.getChannelId())).build())
//            .build();
//    messageRepository.save(message);
//}
//
//
//public FollowChannelDto createFollowChannel(UUID channelId, Long memberId) {
//    ChannelEntity channel = channelRepository.findById(channelId).orElseThrow();
//    MemberEntity member = memberRepository.findById(memberId).orElseThrow();
//    Boolean isExists = followChannelRepository.existsByChannelIdAndMemberId(channelId,memberId);
//    if(Boolean.TRUE.equals(isExists)){
//        return FollowChannelDto.fromEntity(followChannelRepository.findByChannelIdAndMemberId(channelId,memberId));
//    }
//    FollowChannelEntity followChannelEntity = FollowChannelEntity.builder().member(member).channel(channel).build();
//    followChannelRepository.save(followChannelEntity);
//    return FollowChannelDto.fromEntity(followChannelEntity);
//}
//
//public void deleteFollowChannel(UUID channelId,UUID followChannelId) {
//    followChannelRepository.deleteById(followChannelId);
//    ChannelEntity channel = channelRepository.findById(channelId).orElseThrow();
//    if(channel.getFollowChannelEntities().isEmpty()){
//        messageRepository.deleteAllByChannelId(channelId);
//    }
//}
