package org.where.modulecore.domain.friend;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFollowRelationEntity is a Querydsl query type for FollowRelationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFollowRelationEntity extends EntityPathBase<FollowRelationEntity> {

    private static final long serialVersionUID = -85966776L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFollowRelationEntity followRelationEntity = new QFollowRelationEntity("followRelationEntity");

    public final org.where.modulecore.domain.member.QMemberEntity follower;

    public final org.where.modulecore.domain.member.QMemberEntity following;

    public QFollowRelationEntity(String variable) {
        this(FollowRelationEntity.class, forVariable(variable), INITS);
    }

    public QFollowRelationEntity(Path<? extends FollowRelationEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFollowRelationEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFollowRelationEntity(PathMetadata metadata, PathInits inits) {
        this(FollowRelationEntity.class, metadata, inits);
    }

    public QFollowRelationEntity(Class<? extends FollowRelationEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.follower = inits.isInitialized("follower") ? new org.where.modulecore.domain.member.QMemberEntity(forProperty("follower")) : null;
        this.following = inits.isInitialized("following") ? new org.where.modulecore.domain.member.QMemberEntity(forProperty("following")) : null;
    }

}

