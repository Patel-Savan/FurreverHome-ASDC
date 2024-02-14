# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:17-al2023-jdk
MAINTAINER furrever.ca

# Set the working directory in the container
WORKDIR /usr/app

# Copy the compiled JAR file from your target directory into the container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expose the port on which your Spring Boot application will run (adjust as needed)
EXPOSE 8000

# Define the command to run your Spring Boot application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
