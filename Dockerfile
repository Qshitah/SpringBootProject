# Use an official OpenJDK runtime as a parent image
FROM adoptopenjdk:11-jre-hotspot

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container at /app
COPY target/your-spring-boot-app.jar /app/your-spring-boot-app.jar

# Expose the port that your Spring Boot application will run on (default is 8080)
EXPOSE 8080

# Specify the command to run your Spring Boot application
CMD ["java", "-jar", "your-spring-boot-app.jar"]
