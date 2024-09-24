package org.where.moduleapi.api.service.channel;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.where.moduleapi.api.service.channel.dto.ChannelDto;
import org.where.moduleapi.api.service.member.dto.MemberDto;
import org.where.modulecore.domain.channel.*;
import org.where.modulecore.domain.member.MemberEntity;
import org.where.modulecore.domain.member.MemberRepository;
import org.where.moduleapi.api.service.channel.dto.MessageDto;
import org.where.modulecore.domain.message.MessageEntity;
import org.where.modulecore.domain.message.MessageRepository;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ChannelMembershipRepository channelMembershipRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Transactional
    public ChannelDto findOrCreateOneToOneChannel(Long standardMemberId, ChannelDto.CreateOneToOneChannel body) {
        MemberEntity standardMember = findMemberById(standardMemberId);
        MemberEntity targetMember = findMemberById(body.getTargetMemberId());
        //기존 채팅 방이 있는지 확인
        OneToOneChannelEntity oneToOneChannel = findOrCreateOneToOneChannelEntity(standardMemberId,body.getTargetMemberId());
        oneToOneChannel.setName(body.getChannelName());
        channelRepository.save(oneToOneChannel);
        ChannelMembershipEntity channelMembership = findOrCreateChannelMembership(standardMember,oneToOneChannel);
        findOrCreateChannelMembership(targetMember,oneToOneChannel);
        return ChannelDto.fromEntity(channelMembership);
    }

    public List<ChannelDto> getChannelMembershipList(Long memberId){
        return channelMembershipRepository.findAllByMemberId(memberId).stream().map(ChannelDto::fromEntity).toList();
    }
    public List<MessageDto> getChannelMessageList(UUID channelId){
        List<MessageEntity> messageEntities = messageRepository.findAllByChannelId(channelId);
        return messageEntities.stream().map(MessageDto::fromEntity).toList();
    }


    public ChannelDto createChannelMembership(UUID channelId, Long memberId) {
        ChannelEntity channel = channelRepository.findById(channelId).orElseThrow();
        MemberEntity member = memberRepository.findById(memberId).orElseThrow();

        ChannelMembershipEntity channelMembership = findOrCreateChannelMembership(member,channel);

        return ChannelDto.fromEntity(channelMembership);
    }

    public void deleteChannelMembership(UUID channelId, UUID membershipId) {
        channelMembershipRepository.deleteById(membershipId);
        ChannelEntity channel = channelRepository.findById(channelId).orElseThrow();
        if(channel.getMemberships().isEmpty()){
            messageRepository.deleteAllByChannelId(channelId);
        }
    }

    private MemberEntity findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));
    }
    private OneToOneChannelEntity findOrCreateOneToOneChannelEntity(Long standardMemberId,Long targetMemberId){
        return channelRepository.findOneToOneChannel(standardMemberId,targetMemberId).orElseGet(()-> OneToOneChannelEntity.builder()
                    .member1Id(standardMemberId)
                    .member2Id(targetMemberId)
                    .build()
        );
    }

    private ChannelMembershipEntity findOrCreateChannelMembership(MemberEntity member,ChannelEntity channel){
        return channelMembershipRepository.findByChannelIdAndMemberId(channel.getId(),member.getId())
                .orElseGet(()->{
                    ChannelMembershipEntity channelMembership = ChannelMembershipEntity.builder()
                            .channel(channel)
                            .member(member)
                            .build();
                    channelMembershipRepository.save(channelMembership);
                    return channelMembership;
                });
    }

    public List<MemberDto> getChannelMemberList(UUID channelId) {
        ChannelEntity channel = channelRepository.findById(channelId).orElseThrow(EntityNotFoundException::new);
        return channel.getMemberships().stream()
                .map(ChannelMembershipEntity::getMember)
                .map(MemberDto::fromEntity)
                .toList();
    }
}
