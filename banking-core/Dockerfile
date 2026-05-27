# Use lightweight Java 17 runtime
FROM eclipse-temurin:17-jre
# Set working directory inside container
WORKDIR /app
# Copy the built JAR from Maven target folder
COPY target/banking-core-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]