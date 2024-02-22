package com.where.api.config.security;

import com.where.api.domain.MemberEntity;
import com.where.api.repository.MemberRepository;
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
            return CustomUserDetails.builder()
                    .id(memberEntity.getId())
                    .name(memberEntity.getName())
                    .mobile(memberEntity.getMobile())
                    .password(memberEntity.getPassword())
                    .role(memberEntity.getRole().toString())
                    .isEnabled(memberEntity.isEnabled())
                    .build();
        }
        return null;
    }
}
