package com.where.server.api.service.member;

import com.where.server.api.service.auth.CustomUserDetails;
import com.where.server.api.service.member.dto.MemberDto;
import com.where.server.domain.channel.FollowChannelRepository;
import com.where.server.domain.channel.LocationRepository;
import com.where.server.domain.channel.MessageRepository;
import com.where.server.domain.member.MemberEntity;
import com.where.server.domain.member.MemberRepository;
import com.where.server.domain.member.MemberRole;
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
    followChannelRepository.deleteAllByMemberId(id);
    messageRepository.deleteAllByMemberId(id);
    locationRepository.deleteAllByMemberId(id);

    memberRepository.deleteById(id);
    }

    public Boolean existMember(String phoneNumber){
        return memberRepository.existsByPhoneNumber(phoneNumber);
    }

    public CustomUserDetails createMember(String mobile){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setPhoneNumber(mobile);
        memberEntity.setRole(MemberRole.ROLE_USER);
        memberEntity.setEnabled(true);
        memberRepository.save(memberEntity);
        return CustomUserDetails.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .role(memberEntity.getRole().toString())
                .mobile(memberEntity.getPhoneNumber())
                .isEnabled(memberEntity.isEnabled())
                .build();
    }
    public  CustomUserDetails findMember(String phoneNumber){
        MemberEntity memberEntity = memberRepository.findByPhoneNumber(phoneNumber);
        return CustomUserDetails.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .mobile(memberEntity.getPhoneNumber())
                .role(memberEntity.getRole().toString())
                .isEnabled(memberEntity.isEnabled())
                .build();
    }

    public MemberDto getMember(Long id) throws EntityNotFoundException {
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return MemberDto.fromEntity(memberEntity);
    }
}
