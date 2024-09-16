package org.where.modulecore.domain.friend;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class FollowRelationId implements Serializable {
    private Long following;
    private Long follower;
}
