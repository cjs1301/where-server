package org.where.moduleapi.api.service.member;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.where.moduleapi.api.service.member.dto.MemberDto;
import org.where.modulecore.domain.channel.ChannelMembershipRepository;
import org.where.modulecore.domain.member.MemberEntity;
import org.where.modulecore.domain.member.MemberRepository;
import org.where.modulecore.domain.message.LocationRepository;
import org.where.modulecore.domain.message.MessageRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final LocationRepository locationRepository;

    private final MessageRepository messageRepository;

    private final ChannelMembershipRepository channelMembershipRepository;

    @Transactional
    public void deleteMember(Long memberId) {
        findMemberById(memberId);
        channelMembershipRepository.deleteAllByMemberId(memberId);
        messageRepository.deleteAllByMemberId(memberId);
        locationRepository.deleteAllByMemberId(memberId);
        memberRepository.deleteById(memberId);
    }

    public MemberDto getMember(Long memberId) throws EntityNotFoundException {
        MemberEntity memberEntity = findMemberById(memberId);
        return MemberDto.fromEntity(memberEntity);
    }

    @Transactional
    public MemberDto updateMember(Long memberId, MemberDto.Update updateData) {
        return memberRepository.findById(memberId)
                .map(member -> {
                    if (updateData.getName() != null) {
                        member.updateName(updateData.getName());
                    }
                    if (updateData.getProfileImage() != null) {
                        member.updateProfileImage(updateData.getProfileImage());
                    }
                    return MemberDto.fromEntity(memberRepository.save(member));
                })
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));
    }

    public Set<MemberDto> isRegisteredMember(MemberDto.Contact body) {
        Set<String> setPhoneNumberList = new HashSet<>(body.getPhoneNumberList());
        Set<MemberEntity> memberList = isRegisteredMemberByPhoneNumber(setPhoneNumberList);
        return memberList.stream().map(MemberDto::fromEntity).collect(Collectors.toSet());
    }

    private Set<MemberEntity> isRegisteredMemberByPhoneNumber(Set<String> setPhoneNumberList) {
        return memberRepository.findAllByPhoneNumberIn(setPhoneNumberList);
    }
    private MemberEntity findMemberById(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
    }
}
