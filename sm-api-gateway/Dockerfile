# Dockerfile para ms-api-gateway
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/sm-api-gateway-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]