package org.where.modulecore.domain.friend;
import org.where.modulecore.domain.member.MemberEntity;

import java.util.Set;

public interface FollowRelationRepoCustom {
    Set<MemberEntity> findAllByFollowerId(Long followerId);
}
