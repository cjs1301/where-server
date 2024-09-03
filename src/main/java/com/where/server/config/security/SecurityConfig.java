package com.where.server.config.security;

import com.where.server.api.service.auth.CustomUserDetailsService;
import com.where.server.config.log.ApiLoggingFilter;
import com.where.server.config.security.jwt.JWTFilter;
import com.where.server.config.security.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final FirebaseAuthenticationProvider firebaseAuthenticationProvider;
    private final ApiLoggingFilter apiLoggingFilter;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    // PasswordEncoder는 BCryptPasswordEncoder를 사용
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers("/login").permitAll()
                        .requestMatchers("/.well-known/**").permitAll()
                        .requestMatchers("/ws/**", "/actuator/**").permitAll()
                        .requestMatchers("/favicon.ico").permitAll()
                        .anyRequest().authenticated()
                );
        httpSecurity.authenticationProvider(firebaseAuthenticationProvider);

        httpSecurity.addFilterBefore(apiLoggingFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAt(
                new FirebaseAuthenticationFilter(
                        "/login",
                        authenticationManager(authenticationConfiguration),
                        jwtUtil
                ),
                UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAfter(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
