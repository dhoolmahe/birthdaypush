# ---------- Stage 1: Build ----------
FROM maven:3.9.5-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy all source files
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# ---------- Stage 2: Run ----------
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy only the jar from the previous stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port for Render
EXPOSE 8080

# Set entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]
