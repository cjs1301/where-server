FROM public.ecr.aws/lambda/java:21

# 빌드된 JAR 파일 복사
COPY build/libs/*-aws.jar ${LAMBDA_TASK_ROOT}/lib/

# Lambda 함수 핸들러 설정
CMD ["org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest"]
