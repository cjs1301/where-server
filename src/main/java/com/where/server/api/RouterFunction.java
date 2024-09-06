package com.where.server.api;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.where.server.api.controller.*;
import com.where.server.domain.security.CustomUserDetails;
import com.where.server.config.security.jwt.JWTUtil;
import com.where.server.config.security.FirebaseAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RouterFunction {

    @Autowired
    private AuthController authController;
    @Autowired
    private ChannelController channelController;
    @Autowired
    private DeepLinkController deepLinkController;
    @Autowired
    private MeController meController;
    @Autowired
    private MemberController memberController;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private FirebaseAuthenticationProvider firebaseAuthenticationProvider;
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> router() {
        return request -> {
            try {
                String path = request.getPath();
                String httpMethod = request.getHttpMethod();

                // Handle login separately
                if ("/login".equals(path) && "POST".equals(httpMethod)) {
                    return handleLogin(request);
                }

                // JWT Authentication for other endpoints
                String token = extractToken(request);
                if (token == null || jwtUtil.isExpired(token)) {
                    return createResponse(401, "Unauthorized");
                }

                CustomUserDetails userDetails = authenticateUser(token);
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
                );

                // Route to appropriate controller method
                if ("/auth".equals(path) && "GET".equals(httpMethod)) {
                    return createResponse(authController.getAuthInfo(userDetails));
                } else if (path.startsWith("/channels")) {
                    return handleChannelRequests(request, userDetails);
                } else if (path.startsWith("/.well-known")) {
                    return handleDeepLinkRequests(request);
                } else if (path.startsWith("/me")) {
                    return handleMeRequests(request, userDetails);
                } else if ("/members".equals(path) && "DELETE".equals(httpMethod)) {
                    return createResponse(memberController.deleteMember(userDetails));
                }

                return createResponse(404, "Not Found");
            } catch (Exception e) {
                return createResponse(500, "Internal Server Error: " + e.getMessage());
            }
        };
    }

    private APIGatewayProxyResponseEvent handleLogin(APIGatewayProxyRequestEvent request) {
        try {
            String firebaseToken = request.getBody(); // Assuming the token is sent in the body
            Authentication authentication = firebaseAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(firebaseToken, null)
            );
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = jwtUtil.createJwt(userDetails.getId(), userDetails.getUsername(), userDetails.getRole());
            return createResponse(200, jwt);
        } catch (Exception e) {
            return createResponse(401, "Authentication failed: " + e.getMessage());
        }
    }

    private String extractToken(APIGatewayProxyRequestEvent request) {
        String authorization = request.getHeaders().get("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

    private CustomUserDetails authenticateUser(String token) {
        Long id = jwtUtil.getId(token);
        String mobile = jwtUtil.getMobile(token);
        String role = jwtUtil.getRole(token);
        return CustomUserDetails.builder()
                .id(id)
                .phoneNumber(mobile)
                .role(role)
                .isEnabled(true)
                .build();
    }

    private APIGatewayProxyResponseEvent handleChannelRequests(APIGatewayProxyRequestEvent request, CustomUserDetails userDetails) throws Exception {
        String path = request.getPath();
        String httpMethod = request.getHttpMethod();

        if ("/channels".equals(path) && "POST".equals(httpMethod)) {
            return createResponse(channelController.createChannelAndFollow(userDetails, request.getBody()));
        } else if (path.matches("/channels/[^/]+/follow/[^/]+") && "DELETE".equals(httpMethod)) {
            String[] parts = path.split("/");
            return createResponse(channelController.deleteFollowChannelAndChannel(java.util.UUID.fromString(parts[2]), java.util.UUID.fromString(parts[4])));
        } else if ("/channels/follow".equals(path) && "GET".equals(httpMethod)) {
            return createResponse(channelController.getFollowChannelList(userDetails));
        } else if (path.matches("/channels/[^/]+/messages") && "GET".equals(httpMethod)) {
            String[] parts = path.split("/");
            return createResponse(channelController.getChannelMessageList(java.util.UUID.fromString(parts[2])));
        } else if (path.matches("/channels/[^/]+/follow") && "POST".equals(httpMethod)) {
            String[] parts = path.split("/");
            return createResponse(channelController.createFollowChannel(java.util.UUID.fromString(parts[2]), userDetails));
        }

        return new APIGatewayProxyResponseEvent().withStatusCode(404).withBody("Not Found");
    }

    private APIGatewayProxyResponseEvent handleDeepLinkRequests(APIGatewayProxyRequestEvent request) throws Exception {
        String path = request.getPath();

        if ("/.well-known/apple-app-site-association".equals(path)) {
            return createResponse(deepLinkController.iosDeeplinkHandler());
        } else if ("/.well-known/assetlinks.json".equals(path)) {
            return createResponse(deepLinkController.androidDeeplinkHandler());
        }

        return new APIGatewayProxyResponseEvent().withStatusCode(404).withBody("Not Found");
    }

    private APIGatewayProxyResponseEvent handleMeRequests(APIGatewayProxyRequestEvent request, CustomUserDetails userDetails) throws Exception {
        String path = request.getPath();
        String httpMethod = request.getHttpMethod();

        if ("/me".equals(path) && "GET".equals(httpMethod)) {
            return createResponse(meController.getMeInfo(userDetails));
        } else if ("/me/following".equals(path) && "GET".equals(httpMethod)) {
            return createResponse(meController.getMyMyFollowing(userDetails));
        } else if ("/me/following".equals(path) && "POST".equals(httpMethod)) {
            return createResponse(meController.synchronizeContactPhoneNumber(userDetails, objectMapper.readValue(request.getBody(), FollowRelationDto.Create.class)));
        }

        return new APIGatewayProxyResponseEvent().withStatusCode(404).withBody("Not Found");
    }

    private APIGatewayProxyResponseEvent createResponse(ResponseEntity<?> responseEntity) throws Exception {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(responseEntity.getStatusCodeValue())
                .withBody(objectMapper.writeValueAsString(responseEntity.getBody()));
    }
}
