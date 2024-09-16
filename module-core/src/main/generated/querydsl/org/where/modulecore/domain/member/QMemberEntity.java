package org.where.modulecore.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberEntity is a Querydsl query type for MemberEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberEntity extends EntityPathBase<MemberEntity> {

    private static final long serialVersionUID = 1775711705L;

    public static final QMemberEntity memberEntity = new QMemberEntity("memberEntity");

    public final ListPath<org.where.modulecore.domain.channel.FollowChannelEntity, org.where.modulecore.domain.channel.QFollowChannelEntity> followingChannelList = this.<org.where.modulecore.domain.channel.FollowChannelEntity, org.where.modulecore.domain.channel.QFollowChannelEntity>createList("followingChannelList", org.where.modulecore.domain.channel.FollowChannelEntity.class, org.where.modulecore.domain.channel.QFollowChannelEntity.class, PathInits.DIRECT2);

    public final SetPath<org.where.modulecore.domain.friend.FollowRelationEntity, org.where.modulecore.domain.friend.QFollowRelationEntity> followingList = this.<org.where.modulecore.domain.friend.FollowRelationEntity, org.where.modulecore.domain.friend.QFollowRelationEntity>createSet("followingList", org.where.modulecore.domain.friend.FollowRelationEntity.class, org.where.modulecore.domain.friend.QFollowRelationEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAppInstalled = createBoolean("isAppInstalled");

    public final BooleanPath isContactListSynchronized = createBoolean("isContactListSynchronized");

    public final BooleanPath isEnabled = createBoolean("isEnabled");

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath profileImage = createString("profileImage");

    public final EnumPath<MemberRole> role = createEnum("role", MemberRole.class);

    public QMemberEntity(String variable) {
        super(MemberEntity.class, forVariable(variable));
    }

    public QMemberEntity(Path<? extends MemberEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberEntity(PathMetadata metadata) {
        super(MemberEntity.class, metadata);
    }

}

