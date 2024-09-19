package org.where.moduleapi.api.service.member;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.where.moduleapi.api.service.member.dto.MemberDto;
import org.where.modulecore.domain.channel.FollowChannelRepository;
import org.where.modulecore.domain.channel.LocationRepository;
import org.where.modulecore.domain.channel.MessageRepository;
import org.where.modulecore.domain.member.MemberEntity;
import org.where.modulecore.domain.member.MemberRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private FollowChannelRepository followChannelRepository;

    @Transactional
    public void deleteMember(Long id) {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + id));
        followChannelRepository.deleteAllByMemberId(id);
        messageRepository.deleteAllByMemberId(id);
        locationRepository.deleteAllByMemberId(id);

        memberRepository.deleteById(id);
    }

    public MemberDto getMember(Long memberId) throws EntityNotFoundException {
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
        return MemberDto.fromEntity(memberEntity);
    }


    public Set<MemberDto> isRegisteredMember(MemberDto.PhoneNumberList body) {
        Set<String> setPhoneNumberList = new HashSet<>(body.getPhoneNumberList());
        Set<MemberEntity> memberList = isRegisteredMemberByPhoneNumber(setPhoneNumberList);
        return memberList.stream().map(MemberDto::fromEntity).collect(Collectors.toSet());
    }
    private Set<MemberEntity> isRegisteredMemberByPhoneNumber(Set<String> setPhoneNumberList){
        return memberRepository.findAllByPhoneNumberIn(setPhoneNumberList);
    }
}
