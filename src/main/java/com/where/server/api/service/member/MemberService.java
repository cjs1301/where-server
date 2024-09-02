package com.where.server.api.service.member;

import com.where.server.api.service.auth.CustomUserDetails;
import com.where.server.domain.channel.FollowChannelRepository;
import com.where.server.domain.channel.LocationMessageRepository;
import com.where.server.domain.channel.MessageRepository;
import com.where.server.domain.member.MemberEntity;
import com.where.server.domain.member.MemberRepository;
import com.where.server.domain.member.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LocationMessageRepository locationMessageRepository;
    private final MessageRepository messageRepository;
    private final FollowChannelRepository followChannelRepository;

    @Transactional
    public void deleteMember(Long id) {
    followChannelRepository.deleteAllByMemberId(id);
    messageRepository.deleteAllByMemberId(id);
    locationMessageRepository.deleteAllByMemberId(id);

    memberRepository.deleteById(id);
    }

    public Boolean existMember(String mobile){
        return memberRepository.existsByMobile(mobile);
    }

    public CustomUserDetails createMember(String mobile){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMobile(mobile);
        memberEntity.setRole(MemberRole.ROLE_USER);
        memberEntity.setEnabled(true);
        memberRepository.save(memberEntity);
        return CustomUserDetails.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .role(memberEntity.getRole().toString())
                .mobile(memberEntity.getMobile())
                .isEnabled(memberEntity.isEnabled())
                .build();
    }
    public  CustomUserDetails findMember(String mobile){
        MemberEntity memberEntity = memberRepository.findByMobile(mobile);
        return CustomUserDetails.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .mobile(memberEntity.getMobile())
                .role(memberEntity.getRole().toString())
                .isEnabled(memberEntity.isEnabled())
                .build();
    }

}
