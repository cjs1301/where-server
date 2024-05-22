package com.where.api.domain.member;

import com.where.api.domain.channel.repository.FollowChannelRepository;
import com.where.api.domain.channel.repository.LocationMessageRepository;
import com.where.api.domain.channel.repository.MessageRepository;
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


//    // 유저,권한 정보를 가져오는 메소드
//    @Transactional(readOnly = true)
//    public Optional<Member> getUserWithAuthorities(String mobile) {
//        return memberRepository.findOneWithAuthoritiesByMobile(mobile);
//    }
//
//    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
//    @Transactional(readOnly = true)
//    public Optional<Member> getMyUserWithAuthorities() {
//        return SecurityUtil.getCurrentUsername()
//                .flatMap(memberRepository::findOneWithAuthoritiesByMobile);
//    }


}
