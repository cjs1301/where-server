package org.where.moduleapi.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.where.moduleapi.api.service.auth.CustomUserDetails;
import org.where.moduleapi.api.service.member.dto.MemberDto;
import org.where.moduleapi.config.security.jwt.JWTUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class FirebaseAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final String tokenParameter = "firebaseToken";
    private final ObjectMapper objectMapper;

    public FirebaseAuthenticationFilter(String url, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super(new AntPathRequestMatcher(url, "POST"));
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.debug("FirebaseAuthenticationFilter processing login request");
        String firebaseToken = obtainFirebaseToken(request);

        if (firebaseToken == null) {
            throw new AuthenticationServiceException("Firebase Token is missing");
        }

        try {
            // Firebase 토큰 검증
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
            // Firebase에서 확인된 전화번호와 요청의 mobile이 일치하는지 확인
            String phoneNumber = decodedToken.getClaims().get("phone_number").toString();
            log.info("token phone number : {}", phoneNumber);

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(phoneNumber, null, null);
            setDetails(request, authRequest);
            return this.authenticationManager.authenticate(authRequest);
        } catch (FirebaseAuthException e) {
            throw new AuthenticationServiceException("Invalid Firebase token", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        //UserDetailsS
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String mobile = customUserDetails.getPhoneNumber();
        String name = customUserDetails.getName();
        Long id = customUserDetails.getId();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(id, mobile, name, role);

        response.addHeader("Authorization", "Bearer " + token);
        MemberDto memberDto = MemberDto.builder()
                .id(id)
                .name(name)
                .phoneNumber(mobile)
                .profileImage(null) // 안씀
                .isContactListSynchronized(false) // 안씀
                .build();
        // 성공 메시지 추가
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = objectMapper.writeValueAsString(memberDto);
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        //로그인 실패시 401 응답 코드 반환
        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String errorMessage = String.format("{ \"error\": \"%s\" }", failed.getMessage());
            log.error(failed.getMessage());
            response.getWriter().write(errorMessage);
        } catch (IOException e) {
            // IOException이 발생하면 로그를 남기고 계속 진행
            logger.error("Error writing unsuccessful authentication response", e);
        }
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    @Nullable
    protected String obtainFirebaseToken(HttpServletRequest request) {
        return request.getParameter(this.tokenParameter);
    }
}
