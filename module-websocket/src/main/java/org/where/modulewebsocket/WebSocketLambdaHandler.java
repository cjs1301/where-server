//package org.where.modulewebsocket;
//
//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.RequestHandler;
//import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketEvent;
//import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketResponse;
//import com.where.server.api.service.WebSocketService;
//
//public class WebSocketLambdaHandler implements RequestHandler<APIGatewayV2WebSocketEvent, APIGatewayV2WebSocketResponse> {
//
//    private final WebSocketService webSocketService;
//
//    public WebSocketLambdaHandler() {
//        this.webSocketService = new WebSocketService();
//    }
//
//    @Override
//    public APIGatewayV2WebSocketResponse handleRequest(APIGatewayV2WebSocketEvent event, Context context) {
//        String connectionId = event.getRequestContext().getConnectionId();
//        String eventType = event.getRequestContext().getEventType();
//
//        switch (eventType) {
//            case "CONNECT":
//                return webSocketService.handleConnect(connectionId);
//            case "MESSAGE":
//                return webSocketService.handleMessage(connectionId, event.getBody());
//            case "DISCONNECT":
//                return webSocketService.handleDisconnect(connectionId);
//            default:
//                return createErrorResponse("Unsupported event type: " + eventType);
//        }
//    }
//
//    private APIGatewayV2WebSocketResponse createErrorResponse(String errorMessage) {
//        APIGatewayV2WebSocketResponse response = new APIGatewayV2WebSocketResponse();
//        response.setStatusCode(400);
//        response.setBody(errorMessage);
//        return response;
//    }
//}
