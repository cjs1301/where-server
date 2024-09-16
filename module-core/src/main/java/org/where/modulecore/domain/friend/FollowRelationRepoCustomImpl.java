package org.where.modulecore.domain.friend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.where.modulecore.domain.member.MemberEntity;
import org.where.modulecore.domain.member.QMemberEntity;

import java.util.HashSet;
import java.util.Set;

public class FollowRelationRepoCustomImpl implements FollowRelationRepoCustom {
    private JPAQueryFactory queryFactory;
    FollowRelationRepoCustomImpl(JPAQueryFactory jpaQueryFactory){
        this.queryFactory = jpaQueryFactory;
    }

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
