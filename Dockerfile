FROM openjdk:11-jre-slim

WORKDIR /app

# Copy the JAR file from the target directory to the /app directory in the container
COPY target/real-estate-0.0.1-SNAPSHOT /app/your-application.jar

EXPOSE 8080

CMD ["java", "-jar", "your-application.jar"]