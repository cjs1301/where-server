package org.where.moduleapi;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.crac.Core;
import org.crac.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamLambdaHandler implements RequestStreamHandler, Resource {
    public StreamLambdaHandler() {
        // CRaC에 리소스 등록
        Core.getGlobalContext().register(this);
    }
    private static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(ModuleApiApplication.class);
        } catch (ContainerInitializationException e) {
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        handler.proxyStream(inputStream, outputStream, context);
    }
    @Override
    public void beforeCheckpoint(org.crac.Context<? extends Resource> context) {
        // 체크포인트 전에 필요한 작업 수행
        // 예: 데이터베이스 연결 닫기, 캐시 비우기 등
    }

    @Override
    public void afterRestore(org.crac.Context<? extends Resource> context) {
        // 복원 후 필요한 작업 수행
        // 예: 데이터베이스 연결 재설정, 캐시 재구축 등
    }
}
