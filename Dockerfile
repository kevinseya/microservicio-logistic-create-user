# Usar una imagen base con OpenJDK 17
FROM openjdk:17-slim

# Definir el directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración del proyecto
COPY pom.xml ./

# Descargar dependencias usando Maven
RUN apt-get update && apt-get install -y maven && \
    mvn dependency:resolve

# Copiar el código fuente
COPY src ./src

# Construir la aplicación
RUN mvn clean package -DskipTests

# Copiar el archivo JAR generado
COPY target/*.jar app.jar

# Exponer el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
