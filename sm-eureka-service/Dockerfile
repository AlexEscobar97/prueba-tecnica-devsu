# Dockerfile para sm-eureka-service
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/sm-eureka-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]