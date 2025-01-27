FROM gradle:8.12-jdk17-corretto AS build

COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle/
COPY src /app/src/

FROM openjdk:17-jdk-slim

COPY --from=build /app/build/libs/api-gateway-books-0.0.1-SNAPSHOT.jar /app/app.jar

RUN ls -la /app/ && java -version

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
