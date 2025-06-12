FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/mini-social-network-0.0.1-SNAPSHOT.jar /app/application.jar
COPY entrypoint.sh /app/entrypoint.sh

ENTRYPOINT ["/app/entrypoint.sh"]