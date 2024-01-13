package com.where.api.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minidev.json.annotate.JsonIgnore;


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
}
