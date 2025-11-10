# Etapa 1: Build
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Instala o Maven e compila o projeto
RUN apt-get update && apt-get install -y maven && mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
