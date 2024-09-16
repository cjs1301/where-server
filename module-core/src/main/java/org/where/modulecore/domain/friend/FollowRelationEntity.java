package org.where.modulecore.domain.friend;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.where.modulecore.domain.member.MemberEntity;

@Entity
@Table(name = "follow_member")
@IdClass(FollowRelationId.class)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FollowRelationEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    MemberEntity follower;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false)
    MemberEntity following;

}
