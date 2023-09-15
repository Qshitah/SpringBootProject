# Use the official OpenJDK base image for Java 17
FROM adoptopenjdk/openjdk17:alpine-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot application JAR into the container
COPY target/Login-Practice-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application will run on (change this to match your Spring Boot application)
EXPOSE 8080

# Command to run your application
CMD ["java", "-jar", "app.jar"]
