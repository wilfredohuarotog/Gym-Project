FROM maven:3.8.5-openjdk-17-slim AS builder

WORKDIR /app

COPY pom.xml /app

RUN mvn dependency:go-offline

COPY ./src /app/src

RUN mvn clean package -Dmaven.test.skip=true


FROM openjdk:17.0.1-jdk-slim

COPY --from=builder /app/target/*.jar /app.jar

ENTRYPOINT ["java","-jar","app.jar"]

