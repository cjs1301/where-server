package org.where.moduleapi.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.where.moduleapi.api.service.auth.CustomUserDetailsService;


@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phoneNumber = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByMobile(phoneNumber);

        // Firebase 토큰은 이미 FirebaseAuthenticationFilter에서 검증되었다고 가정합니다.
        // 여기서는 추가적인 검증 없이 인증을 수행합니다.

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
