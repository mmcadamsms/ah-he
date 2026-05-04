FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 3000
ENV SERVER_PORT=3000
ENTRYPOINT ["java", "-jar", "app.jar"]
