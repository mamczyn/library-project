# Maven Builder
FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /build

COPY ../../ .

RUN mvn clean install

# Maven Runner
FROM openjdk:17-jdk-alpine

WORKDIR /api

COPY --from=builder /build/library-api/target/library-api-0.0.1-SNAPSHOT.jar api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api.jar"]
