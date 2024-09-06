package com.where.server.domain.friend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.where.server.domain.member.MemberEntity;
import com.where.server.domain.member.QMemberEntity;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class FollowRelationRepoCustomImpl implements FollowRelationRepoCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Set<MemberEntity> findAllByFollowerId(Long followerId){
        QFollowRelationEntity followRelation = QFollowRelationEntity.followRelationEntity;
        QMemberEntity member = QMemberEntity.memberEntity;
        return new HashSet<>(queryFactory
                .select(followRelation.following)
                .from(followRelation)
                .join(followRelation.following, member)
                .where(followRelation.follower.id.eq(followerId))
                .fetch());
    }
}
