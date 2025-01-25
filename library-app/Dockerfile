# Maven Builder
FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /build

COPY ../../ .

RUN mvn clean install

# Maven Runner
FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /build/library-app/target/library-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]