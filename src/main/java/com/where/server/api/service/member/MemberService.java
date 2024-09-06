package com.where.server.api.service.member;

import com.where.server.api.service.member.dto.MemberDto;
import com.where.server.domain.channel.FollowChannelRepository;
import com.where.server.domain.channel.LocationRepository;
import com.where.server.domain.channel.MessageRepository;
import com.where.server.domain.member.MemberEntity;
import com.where.server.domain.member.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
