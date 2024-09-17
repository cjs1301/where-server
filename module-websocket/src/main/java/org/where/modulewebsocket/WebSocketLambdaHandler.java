package org.where.modulewebsocket;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketResponse;
import org.springframework.stereotype.Component;
import org.where.modulewebsocket.service.MessageService;
import org.where.modulewebsocket.service.WebSocketConnectionService;


import java.util.UUID;
import java.util.function.Function;

@Component
public class WebSocketLambdaHandler implements Function<APIGatewayV2WebSocketEvent, APIGatewayV2WebSocketResponse> {

    private final WebSocketConnectionService connectionService;
    private final MessageService messageService;

    public WebSocketLambdaHandler(WebSocketConnectionService connectionService, MessageService messageService) {
        this.connectionService = connectionService;
        this.messageService = messageService;
    }

    @Override
    public APIGatewayV2WebSocketResponse apply(APIGatewayV2WebSocketEvent event) {
        String connectionId = event.getRequestContext().getConnectionId();
        String routeKey = event.getRequestContext().getRouteKey();

        try {
            switch (routeKey) {
                case "$connect":
                    return handleConnect(connectionId, event);
                case "$disconnect":
                    return handleDisconnect(connectionId);
                case "$default":
                    return handleMessage(connectionId, event.getBody());
                default:
                    return createResponse(400, "Unsupported route: " + routeKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return createResponse(500, "Internal server error: " + e.getMessage());
        }
    }

    private APIGatewayV2WebSocketResponse handleConnect(String connectionId, APIGatewayV2WebSocketEvent event) {
        // 이 부분은 실제 구현에서 적절히 수정해야 합니다.
        // 예를 들어, 쿼리 파라미터나 헤더에서 memberId와 channelId를 추출해야 할 수 있습니다.
        Long memberId = Long.getLong(event.getQueryStringParameters().get("memberId"));
        UUID channelId = UUID.fromString(event.getQueryStringParameters().get("channelId"));

        connectionService.handleConnect(connectionId, memberId, channelId);
        return createResponse(200, "Connected.");
    }

    private APIGatewayV2WebSocketResponse handleDisconnect(String connectionId) {
        connectionService.handleDisconnect(connectionId);
        return createResponse(200, "Disconnected.");
    }

    private APIGatewayV2WebSocketResponse handleMessage(String connectionId, String body) throws Exception {
        messageService.routeMessage(connectionId, body);
        return createResponse(200, "Message processed.");
    }

    private APIGatewayV2WebSocketResponse createResponse(int statusCode, String body) {
        APIGatewayV2WebSocketResponse response = new APIGatewayV2WebSocketResponse();
        response.setStatusCode(statusCode);
        response.setBody(body);
        return response;
    }
}
