#FROM eclipse-temurin:17-jdk
#
#WORKDIR /app
#COPY . /app
#
#RUN ./mvnw clean package -DskipTests
#
#CMD ["java", "-jar", "target/birthdayreminder-0.0.1-SNAPSHOT.jar"]


# Use a lightweight JVM base image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy your Maven target jar file into the image
COPY target/birthdayreminder-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
