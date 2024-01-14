FROM openjdk:17-jdk-slim

WORKDIR /app
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8081

ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","app.jar"]
