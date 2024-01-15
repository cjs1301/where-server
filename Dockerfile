FROM openjdk:17-jdk-slim

WORKDIR /app
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 4000

HEALTHCHECK --interval=30s --retries=5 --start-period=5s --timeout=3s \
            CMD wget --no-verbose --tries=1 --spider localhost:4000/actuator/health || exit 1

ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","app.jar"]
