package com.where.api.domain.member.entity;

import com.where.api.domain.chating.entity.FollowChannelEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;


@Entity
@Table(name = "member")
@Getter
@Setter
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

    @Column(name = "mobile",unique = true)
    String mobile;

    @Column(length = 100)
    String password;

    MemberRole role;

    @OneToMany(mappedBy = "member")
    List<FollowChannelEntity> followingChannelList;
}
