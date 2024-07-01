package com.where.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@Slf4j
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/topic");
//        registry.enableStompBrokerRelay("/topic")
//                .setRelayHost("rabbitmq")
//                .setClientLogin("guest")
//                .setClientPasscode("guest")
//                .setRelayPort(61613)
//                .setVirtualHost("/");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                .setAllowedOrigins("*");
    }
}
