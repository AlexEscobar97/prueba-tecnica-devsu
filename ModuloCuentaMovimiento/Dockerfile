# Dockerfile para ms-cuenta-movimiento
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/ModuloCuentaMovimiento-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]