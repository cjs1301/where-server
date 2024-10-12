package org.where.moduleapi.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.where.moduleapi.api.service.auth.CustomUserDetails;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        //토큰 소멸 시간 검증
        if (Boolean.TRUE.equals(jwtUtil.isExpired(token))) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token has expired");

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }
        try {
            // 토큰에서 username과 role 획득
            String mobile = jwtUtil.getPhoneNumber(token);
            Long id = jwtUtil.getId(token);
            String role = jwtUtil.getRole(token);
            String name = jwtUtil.getName(token);

            // UserDetails에 회원 정보 객체 담기
            CustomUserDetails customUserDetails = CustomUserDetails.builder()
                    .id(id)
                    .name(name)
                    .phoneNumber(mobile)
                    .role(role)
                    .isEnabled(true)
                    .build();

            // 스프링 시큐리티 인증 토큰 생성
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            // 세션에 사용자 등록
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid token: " + e.getMessage());
        }
    }
    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) {
        try {
            response.setStatus(status.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("status", status.value());
            errorDetails.put("error", status.getReasonPhrase());
            errorDetails.put("message", message);

            objectMapper.writeValue(response.getWriter(), errorDetails);
        } catch (IOException e) {
            // IOException이 발생하면 로그를 남기고 계속 진행
            logger.error("Error writing unsuccessful authentication response", e);
        }
    }
}
