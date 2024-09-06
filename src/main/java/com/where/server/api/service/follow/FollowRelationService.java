package com.where.server.api.service.follow;

import com.where.server.api.service.member.dto.MemberDto;
import com.where.server.domain.friend.FollowRelationEntity;
import com.where.server.domain.friend.FollowRelationRepository;
import com.where.server.domain.member.MemberEntity;
import com.where.server.domain.member.MemberRepository;
import com.where.server.domain.member.MemberRole;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FollowRelationService {
    @Autowired
    private FollowRelationRepository followRelationRepository;
    @Autowired
    private MemberRepository memberRepository;

    public Set<MemberDto> getMyFollowing(Long standardMemberId) {
        return followRelationRepository.findAllByFollowerId(standardMemberId).stream()
                .map(MemberDto::fromEntity)
                .collect(Collectors.toSet());
    }
    public Set<MemberDto> createMyFollowRelation(Long standardMemberId, FollowRelationDto.Create body){
        List<String> friendPhoneNumberList = body.phoneNumberList;

        MemberEntity standardMember = memberRepository.findById(standardMemberId).orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + standardMemberId));

        Set<MemberEntity> memberEntitySet = this.findOrCreateMember(friendPhoneNumberList);

        Set<FollowRelationEntity> newFollowRelations = memberEntitySet.stream()
                .map(memberEntity -> FollowRelationEntity.builder()
                        .follower(standardMember)
                        .following(memberEntity)
                        .build()
                ).collect(Collectors.toSet());

        followRelationRepository.saveAll(newFollowRelations);

        return memberEntitySet.stream().map(MemberDto::fromEntity).collect(Collectors.toSet());
    }

    private Set<MemberEntity> findOrCreateMember(List<String> phoneNumberList){
        Set<String> uniquePhoneNumbers = new HashSet<>(phoneNumberList);

        Set<MemberEntity> existingMembers = memberRepository.findAllByPhoneNumberIn(uniquePhoneNumbers);

        Set<String> existingPhoneNumbers = existingMembers.stream()
                .map(MemberEntity::getPhoneNumber)
                .collect(Collectors.toSet());

        Set<MemberEntity> newMembers = uniquePhoneNumbers.stream()
                .filter(phoneNumber -> !existingPhoneNumbers.contains(phoneNumber))
                .map(this::createMemberEntity)
                .collect(Collectors.toSet());

        if (!newMembers.isEmpty()) {
            memberRepository.saveAll(newMembers);
        }
        Set<MemberEntity> allMembers = new HashSet<>(existingMembers);
        allMembers.addAll(newMembers);
        return allMembers;
    }

    private MemberEntity createMemberEntity(String phoneNumber){
        return MemberEntity.builder()
                .phoneNumber(phoneNumber)
                .isAppInstalled(false)
                .isEnabled(false)
                .isContactListSynchronized(false)
                .role(MemberRole.ROLE_USER)
                .build();
    }
}
