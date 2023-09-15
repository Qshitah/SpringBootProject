# Use a base image with Java 17
FROM adoptopenjdk:17-jre-hotspot

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR/WAR file into the container at /app
COPY target/your-app.jar /app/your-app.jar

# Expose the port your Spring Boot application listens on (usually 8080)
EXPOSE 8080

# Define the command to run your Spring Boot application
CMD ["java", "-jar", "your-app.jar"]
