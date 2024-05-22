package com.where.api.domain.member;


import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;


//@Entity
@Table(name = "member")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "member_id", nullable = false)
    @Id
    Long id;

//    @Column(name = "name", length = 50)
    String name;

    @JsonIgnore
//    @Column(name = "is_enabled")
            @Column("is_enabled")
    boolean isEnabled;

//    @Column(name = "mobile",unique = true)
    @Column()
    String mobile;

//    @Column(length = 100)
    String password;

    MemberRole role;

//    @OneToMany(mappedBy = "member")
//    List<FollowChannelEntity> followingChannelList;
}
