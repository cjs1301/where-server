package com.where.api.core.security;

import com.where.api.domain.member.entity.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final MemberEntity memberEntity;

    public CustomUserDetails(MemberEntity memberEntity) {

        this.memberEntity = memberEntity;
    }

    public Long getId(){
        return memberEntity.getId();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add((GrantedAuthority) () -> String.valueOf(memberEntity.getRole()));

        return collection;
    }

    @Override
    public String getPassword() {

        return memberEntity.getPassword();
    }

    @Override
    public String getUsername() {

        return memberEntity.getMobile();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return memberEntity.isEnabled();
    }
}
