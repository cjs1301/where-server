package com.where.api.core.security;

import com.where.api.domain.member.entity.MemberEntity;
import com.where.api.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String mobile)throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByMobile(mobile);
        if (memberEntity != null) {
            System.out.println("유저 찾음");
            return new CustomUserDetails(memberEntity);
        }
        System.out.println("유저를 찾을수 없음");
        return null;
    }
}
