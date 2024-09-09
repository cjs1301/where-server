package com.where.server.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LocalHttpController {

    @Autowired
    private RouterFunction routerFunction;

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<String> handleRequest(HttpServletRequest request, @RequestBody(required = false) String body) throws IOException {
        APIGatewayProxyRequestEvent apiGatewayRequest = convertToApiGatewayRequest(request, body);
        APIGatewayProxyResponseEvent response = routerFunction.router().apply(apiGatewayRequest);

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response.getBody());
    }

    private APIGatewayProxyRequestEvent convertToApiGatewayRequest(HttpServletRequest request, String body) throws IOException {
        APIGatewayProxyRequestEvent apiGatewayRequest = new APIGatewayProxyRequestEvent();

        // Set path
        apiGatewayRequest.setPath(request.getRequestURI());

        // Set HTTP method
        apiGatewayRequest.setHttpMethod(request.getMethod());

        // Set headers
        Map<String, String> headers = new HashMap<>();
        request.getHeaderNames().asIterator().forEachRemaining(headerName ->
                headers.put(headerName, request.getHeader(headerName)));
        apiGatewayRequest.setHeaders(headers);

        // Set query string parameters
        Map<String, String> queryStringParameters = new HashMap<>();
        request.getParameterMap().forEach((key, value) -> queryStringParameters.put(key, value[0]));
        apiGatewayRequest.setQueryStringParameters(queryStringParameters);

        // Set body
        apiGatewayRequest.setBody(body);

        return apiGatewayRequest;
    }
}
