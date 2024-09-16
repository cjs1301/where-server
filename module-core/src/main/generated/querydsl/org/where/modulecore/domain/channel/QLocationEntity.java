package org.where.modulecore.domain.channel;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocationEntity is a Querydsl query type for LocationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationEntity extends EntityPathBase<LocationEntity> {

    private static final long serialVersionUID = 971294619L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocationEntity locationEntity = new QLocationEntity("locationEntity");

    public final org.where.modulecore.domain.QTimeStamped _super = new org.where.modulecore.domain.QTimeStamped(this);

    public final QChannelEntity channel;

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final org.where.modulecore.domain.member.QMemberEntity member;

    public final ComparablePath<org.locationtech.jts.geom.LineString> route = createComparable("route", org.locationtech.jts.geom.LineString.class);

    //inherited
    public final DateTimePath<java.time.OffsetDateTime> updatedAt = _super.updatedAt;

    public QLocationEntity(String variable) {
        this(LocationEntity.class, forVariable(variable), INITS);
    }

    public QLocationEntity(Path<? extends LocationEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLocationEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLocationEntity(PathMetadata metadata, PathInits inits) {
        this(LocationEntity.class, metadata, inits);
    }

    public QLocationEntity(Class<? extends LocationEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.channel = inits.isInitialized("channel") ? new QChannelEntity(forProperty("channel")) : null;
        this.member = inits.isInitialized("member") ? new org.where.modulecore.domain.member.QMemberEntity(forProperty("member")) : null;
    }

}

