
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/gestao-0.0.5-BETA.jar /app/app.jar

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
