package org.where.modulewebsocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.apigatewaymanagementapi.ApiGatewayManagementApiClient;

import java.net.URI;

@Configuration
public class AwsApiGatewayConfig {
    @Bean
    public ApiGatewayManagementApiClient apiGatewayManagementApiClient() {
        return ApiGatewayManagementApiClient.builder()
                .endpointOverride(URI.create("https://pt76s4l1ag.execute-api.ap-northeast-2.amazonaws.com/prod"))
                .region(Region.AP_NORTHEAST_2)
                .build();
    }
}


