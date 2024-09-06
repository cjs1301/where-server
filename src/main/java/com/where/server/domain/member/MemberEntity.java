package com.where.server.domain.member;

import com.where.server.domain.channel.FollowChannelEntity;
import com.where.server.domain.friend.FollowRelationEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minidev.json.annotate.JsonIgnore;

import java.util.*;
import java.util.stream.Collectors;


@Entity
@Table(name = "member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    Long id;

    @Column(name = "name", length = 50)
    String name;

    @JsonIgnore
    @Column(name = "is_enabled")
    boolean isEnabled;


    @Column(name = "phone_number", unique = true)
    String phoneNumber;

    String profileImage;

    @Column(name = "is_app_installed", nullable = false)
    Boolean isAppInstalled;

    @Column(name = "is_contact_list_synchronized", nullable = false)
    Boolean isContactListSynchronized;

    @Enumerated(EnumType.STRING)
    MemberRole role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<FollowChannelEntity> followingChannelList;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.REMOVE, orphanRemoval = true)
    Set<FollowRelationEntity> followingList;

    public Set<MemberEntity> getFollowingMembers() {
        return followingList.stream()
                .map(FollowRelationEntity::getFollowing)
                .collect(Collectors.toSet());
    }
}
