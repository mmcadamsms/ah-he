FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY backend/background-worker/build/libs/background-worker.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
