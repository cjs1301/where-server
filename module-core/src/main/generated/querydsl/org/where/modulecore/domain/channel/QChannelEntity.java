package org.where.modulecore.domain.channel;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChannelEntity is a Querydsl query type for ChannelEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChannelEntity extends EntityPathBase<ChannelEntity> {

    private static final long serialVersionUID = 111872707L;

    public static final QChannelEntity channelEntity = new QChannelEntity("channelEntity");

    public final org.where.modulecore.domain.QTimeStamped _super = new org.where.modulecore.domain.QTimeStamped(this);

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> createdAt = _super.createdAt;

    public final ListPath<FollowChannelEntity, QFollowChannelEntity> followChannelEntities = this.<FollowChannelEntity, QFollowChannelEntity>createList("followChannelEntities", FollowChannelEntity.class, QFollowChannelEntity.class, PathInits.DIRECT2);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> updatedAt = _super.updatedAt;

    public QChannelEntity(String variable) {
        super(ChannelEntity.class, forVariable(variable));
    }

    public QChannelEntity(Path<? extends ChannelEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChannelEntity(PathMetadata metadata) {
        super(ChannelEntity.class, metadata);
    }

}

