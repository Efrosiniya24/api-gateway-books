FROM ubuntu:latest
LABEL authors="Frosya"

FROM openjdk:17-jdk-slim
FROM gradle:8.12-jdk21 AS BUILD
EXPOSE 8083

WORKDIR /app/

COPY build.gradle settings.gradle gradlew /app/gradle/project/
COPY gradle /app/gradle/project/gradle/
COPY src /home/gradle/project/src/

COPY --from=BUILD build/libs/api-gateway-books-0.0.1-SNAPSHOT.jar /app/app.jar

RUN ls -la /app/ && java -version

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

