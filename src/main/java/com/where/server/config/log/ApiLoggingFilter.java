package com.where.server.config.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiLoggingFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(ApiLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long timeTaken = System.currentTimeMillis() - startTime;
            String requestBody = getStringValue(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
            String responseBody = getStringValue(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

            logWithAppropriateLevel(
                    request.getMethod(),
                    request.getRequestURI(),
                    requestBody,
                    response.getStatus(),
                    responseBody,
                    timeTaken,
                    getClientIp(request),
                    request.getHeader("User-Agent"),
                    getHeadersInfo(request)
            );

            responseWrapper.copyBodyToResponse();
        }
    }

    private void logWithAppropriateLevel(String method, String uri, String requestPayload, int status,
                                         String response, long timeTaken, String clientIp, String userAgent,
                                         String headers) {
        String logMessage = String.format(
                "API_CALL method=%s, uri=%s, request_payload=%s, response_code=%d, response=%s, time_taken=%dms, " +
                        "client_ip=%s, user_agent=%s, headers=%s",
                method, uri, requestPayload, status, response, timeTaken, clientIp, userAgent, headers
        );

        if (status >= 500) {
            log.error(logMessage);
        } else if (status >= 400) {
            log.warn(logMessage);
        } else if (timeTaken > 5000) {  // 예: 5초 이상 걸리는 요청은 WARN으로 로깅
            log.warn(logMessage);
        } else {
            log.info(logMessage);
        }
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, characterEncoding);
        } catch (Exception e) {
            return "[Unknown]";
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        }
        return xForwardedForHeader.split(",")[0];
    }

    private String getHeadersInfo(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames())
                .stream()
                .filter(headerName -> !headerName.equalsIgnoreCase("Authorization")) // Exclude Authorization header
                .collect(Collectors.toMap(
                        headerName -> headerName,
                        request::getHeader,
                        (v1, v2) -> v1 + "," + v2
                )).toString();
    }
}
