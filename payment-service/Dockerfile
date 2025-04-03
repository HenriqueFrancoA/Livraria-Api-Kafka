FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY target/*.jar app.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "/app.jar"]