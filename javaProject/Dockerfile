# Use Java 17
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the application (using mvn directly as mvnw might not have execute permissions)
RUN mvn clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the application
# We use a shell form to handle the wildcard or specify the exact jar
CMD ["sh", "-c", "java -jar target/api-0.0.1-SNAPSHOT.jar"]
