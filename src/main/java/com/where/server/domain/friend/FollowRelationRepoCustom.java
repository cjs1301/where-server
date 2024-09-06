package com.where.server.domain.friend;

import com.where.server.domain.member.MemberEntity;

import java.util.Set;

public interface FollowRelationRepoCustom {
    Set<MemberEntity> findAllByFollowerId(Long followerId);
}
