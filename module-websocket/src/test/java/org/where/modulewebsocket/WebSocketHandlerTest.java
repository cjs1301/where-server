//package org.where.modulewebsocket;
//
//import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketEvent;
//import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketResponse;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.function.context.FunctionCatalog;
//import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.function.Function;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@FunctionalSpringBootTest
//@ActiveProfiles("test")
//class WebSocketHandlerTest {
//
//    @Autowired
//    private FunctionCatalog catalog;
//
//    @Test
//    void testWebSocketHandler() {
//        Function<APIGatewayV2WebSocketEvent, APIGatewayV2WebSocketResponse> function =
//                catalog.lookup(Function.class, "webSocketHandler");
//        assertNotNull(function);
//
//        APIGatewayV2WebSocketEvent event = new APIGatewayV2WebSocketEvent();
//        APIGatewayV2WebSocketEvent.RequestContext requestContext = new APIGatewayV2WebSocketEvent.RequestContext();
//        requestContext.setConnectionId("test-connection-id");
//        requestContext.setRouteKey("$default");
//        event.setRequestContext(requestContext);
//        event.setBody("Test message");
//
//        APIGatewayV2WebSocketResponse response = function.apply(event);
//
//        assertNotNull(response);
//        assertEquals(200, response.getStatusCode());
//    }
//}
