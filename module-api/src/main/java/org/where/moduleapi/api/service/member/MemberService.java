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


}
