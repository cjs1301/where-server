//package org.where.modulecore.service;
//
//
//import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketResponse;
//import software.amazon.awssdk.core.SdkBytes;
//import software.amazon.awssdk.services.apigatewaymanagementapi.model.PostToConnectionRequest;
//
////import java.net.URI;
//
//public class WebSocketService {
//
////    private final ApiGatewayManagementApiClient apiGatewayClient;
////
////    public WebSocketService() {
////        this.apiGatewayClient = ApiGatewayManagementApiClient.builder()
////                .endpointOverride(URI.create(System.getenv("WEBSOCKET_API_ENDPOINT")))
////                .build();
////    }
//
//    public APIGatewayV2WebSocketResponse handleConnect(String connectionId) {
//        // Handle new connection
//        return createResponse(200, "Connected");
//    }
//
//    public APIGatewayV2WebSocketResponse handleMessage(String connectionId, String message) {
//        // Process the message and send a response
//        sendMessageToClient(connectionId, "Received: " + message);
//        return createResponse(200, "Message processed");
//    }
//
//    public APIGatewayV2WebSocketResponse handleDisconnect(String connectionId) {
//        // Handle disconnection
//        return createResponse(200, "Disconnected");
//    }
//
//    private void sendMessageToClient(String connectionId, String message) {
//        PostToConnectionRequest postRequest = PostToConnectionRequest.builder()
//                .connectionId(connectionId)
//                .data(SdkBytes.fromUtf8String(message))
//                .build();
//
////        apiGatewayClient.postToConnection(postRequest);
//    }
//
//    private APIGatewayV2WebSocketResponse createResponse(int statusCode, String body) {
//        APIGatewayV2WebSocketResponse response = new APIGatewayV2WebSocketResponse();
//        response.setStatusCode(statusCode);
//        response.setBody(body);
//        return response;
//    }
//}
