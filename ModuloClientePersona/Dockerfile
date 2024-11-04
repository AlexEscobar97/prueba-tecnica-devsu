# Usar una imagen base de OpenJDK
FROM openjdk:11-jre-slim

# Definir el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR al contenedor
COPY target/ModuloClientePersona-0.0.1-SNAPSHOT.jar app.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "modulo-cliente-persona.jar"]
