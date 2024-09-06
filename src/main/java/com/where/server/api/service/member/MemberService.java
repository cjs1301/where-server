package com.where.server.api.service.member;

import com.where.server.api.service.member.dto.MemberDto;
import com.where.server.domain.channel.FollowChannelRepository;
import com.where.server.domain.channel.LocationRepository;
import com.where.server.domain.channel.MessageRepository;
import com.where.server.domain.member.MemberEntity;
import com.where.server.domain.member.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;
    private final MessageRepository messageRepository;
    private final FollowChannelRepository followChannelRepository;

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
