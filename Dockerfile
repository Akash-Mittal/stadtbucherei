# Use a base image with Java 17
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the build files (adjust the path to match your Gradle build output)
COPY build/libs/stadtbucheri-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (default for Spring Boot)
EXPOSE 9091

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
