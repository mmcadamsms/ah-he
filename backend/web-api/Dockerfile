FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY backend/web-api/build/libs/web-api.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
