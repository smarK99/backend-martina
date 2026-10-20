# ==========================================
# Etapa 1: Construcción (Build)
# ==========================================
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copiamos los archivos de Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Damos permisos de ejecución al wrapper (Evita errores en Linux/Render)
RUN chmod +x gradlew

# Copiamos el código fuente
COPY src src

# Compilamos el proyecto (omitiendo los tests para mayor velocidad)
RUN ./gradlew bootJar -x test --no-daemon

# ==========================================
# Etapa 2: Ejecución (Run)
# ==========================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Exponemos el puerto 8080
EXPOSE 8080

# Copiamos el .jar generado en la Etapa 1
# Nota: Verifica que el nombre "inicial1-0.0.1-SNAPSHOT.jar" sea el correcto.
COPY --from=build /app/build/libs/inicial1-0.0.1-SNAPSHOT.jar app.jar

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]