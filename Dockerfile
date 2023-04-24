FROM maven:3.9.0-eclipse-temurin-19 AS builder

WORKDIR /app

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY src src

RUN mvn package -Dmaven.test.skip=true

FROM eclipse-temurin:19-jre

WORKDIR /app

COPY --from=builder /app/target/server-0.0.1-SNAPSHOT.jar server.jar

ENV PORT=8080

ENTRYPOINT java -jar -Dserver.port=${PORT} server.jar