package org.where.moduleapi.api.service.follow;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.where.moduleapi.api.service.member.dto.MemberDto;
import org.where.modulecore.domain.friend.FollowRelationEntity;
import org.where.modulecore.domain.friend.FollowRelationRepository;
import org.where.modulecore.domain.member.MemberEntity;
import org.where.modulecore.domain.member.MemberRepository;
import org.where.modulecore.domain.member.MemberRole;

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

    public FollowRelationService(FollowRelationRepository followRelationRepository, MemberRepository memberRepository) {
        this.followRelationRepository = followRelationRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public Set<MemberDto> getMyFollowing(Long standardMemberId) {
        return followRelationRepository.findAllByFollowerId(standardMemberId).stream()
                .map(MemberDto::fromEntity)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Set<MemberDto> createMyFollowRelationList(Long standardMemberId, FollowRelationDto.CreateList body) {
        MemberEntity standardMember = findMemberById(standardMemberId);
        Set<MemberEntity> membersToFollow = findRegisteredMembers(body.getPhoneNumberList());
        Set<FollowRelationEntity> newFollowRelations = createFollowRelations(standardMember, membersToFollow);
        followRelationRepository.saveAll(newFollowRelations);
        return convertToMemberDtoSet(newFollowRelations);
    }

    @Transactional
    public MemberDto createMyFollowRelation(Long standardMemberId, FollowRelationDto.Create body) {
        MemberEntity standardMember = findMemberById(standardMemberId);
        MemberEntity followingMember = findMemberByPhoneNumber(body.getPhoneNumber());
        FollowRelationEntity newFollowRelation = createFollowRelation(standardMember, followingMember);
        followRelationRepository.save(newFollowRelation);
        return MemberDto.fromEntity(followingMember);
    }

    private Set<MemberEntity> findRegisteredMembers(List<String> phoneNumbers) {
        Set<String> uniquePhoneNumbers = new HashSet<>(phoneNumbers);
        return memberRepository.findAllByPhoneNumberIn(uniquePhoneNumbers);
    }

    private MemberEntity findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));
    }

    private MemberEntity findMemberByPhoneNumber(String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new EntityNotFoundException("Member not found with phoneNumber: " + phoneNumber));
    }

    private Set<FollowRelationEntity> createFollowRelations(MemberEntity follower, Set<MemberEntity> followings) {
        return followings.stream()
                .map(following -> createFollowRelation(follower, following))
                .collect(Collectors.toSet());
    }

    private FollowRelationEntity createFollowRelation(MemberEntity follower, MemberEntity following) {
        return FollowRelationEntity.builder()
                .follower(follower)
                .following(following)
                .build();
    }

    private Set<MemberDto> convertToMemberDtoSet(Set<FollowRelationEntity> followRelations) {
        return followRelations.stream()
                .map(relation -> MemberDto.fromEntity(relation.getFollowing()))
                .collect(Collectors.toSet());
    }
}
