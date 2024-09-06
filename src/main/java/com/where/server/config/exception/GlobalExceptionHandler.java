package com.where.server.config.exception;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;
import java.util.function.Function;

@Slf4j
public class GlobalExceptionHandler implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent request) {
        try {
            // 여기서 실제 요청 처리 로직을 호출합니다.
            // 예: return routerFunction.apply(request);
            throw new UnsupportedOperationException("Not implemented");
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private APIGatewayProxyResponseEvent handleException(Exception e) {
        if (e instanceof BusinessException) {
            return handleBusinessException((BusinessException) e);
        } else if (e instanceof IllegalArgumentException) {
            return handleIllegalArgumentException((IllegalArgumentException) e);
        }
        // 다른 예외 처리...
        return handleGenericException(e);
    }

    private APIGatewayProxyResponseEvent handleBusinessException(BusinessException e) {
        ErrorResponse response = ErrorResponse.of(e.getErrorCode());
        return response.toAPIGatewayProxyResponseEvent();
    }

    private APIGatewayProxyResponseEvent handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse response = ErrorResponse.of(ErrorCode.BAD_REQUEST);
        response.setMessage(e.getMessage());
        return response.toAPIGatewayProxyResponseEvent();
    }

    private APIGatewayProxyResponseEvent handleGenericException(Exception e) {
        ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return response.toAPIGatewayProxyResponseEvent();
    }


}
