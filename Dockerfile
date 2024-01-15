FROM openjdk:17-jdk-slim

WORKDIR /app
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 4000

ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","app.jar"]
