package org.where.modulecore.domain.channel;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFollowChannelEntity is a Querydsl query type for FollowChannelEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFollowChannelEntity extends EntityPathBase<FollowChannelEntity> {

    private static final long serialVersionUID = 1226937554L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFollowChannelEntity followChannelEntity = new QFollowChannelEntity("followChannelEntity");

    public final QChannelEntity channel;

    public final StringPath connectionId = createString("connectionId");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final org.where.modulecore.domain.member.QMemberEntity member;

    public QFollowChannelEntity(String variable) {
        this(FollowChannelEntity.class, forVariable(variable), INITS);
    }

    public QFollowChannelEntity(Path<? extends FollowChannelEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFollowChannelEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFollowChannelEntity(PathMetadata metadata, PathInits inits) {
        this(FollowChannelEntity.class, metadata, inits);
    }

    public QFollowChannelEntity(Class<? extends FollowChannelEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.channel = inits.isInitialized("channel") ? new QChannelEntity(forProperty("channel")) : null;
        this.member = inits.isInitialized("member") ? new org.where.modulecore.domain.member.QMemberEntity(forProperty("member")) : null;
    }

}

