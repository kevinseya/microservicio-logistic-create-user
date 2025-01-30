# Usar una imagen base con OpenJDK 17
FROM openjdk:17-slim AS build

# Definir el directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración del proyecto
COPY pom.xml ./

# Descargar dependencias usando Maven
RUN apt-get update && apt-get install -y maven && \
    mvn dependency:resolve

# Copiar el código fuente
COPY src ./src

# Construir la aplicación (asegurándonos de crear el JAR en el directorio correcto)
RUN mvn clean package -DskipTests

# Segunda etapa para ejecutar la aplicación
FROM openjdk:17-slim

# Definir el directorio de trabajo
WORKDIR /app

# Verificar que el archivo JAR existe (esto es útil para depuración)
RUN ls /app/target/

# Copiar el archivo JAR generado en la fase anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
