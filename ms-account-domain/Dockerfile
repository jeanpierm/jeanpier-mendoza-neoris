FROM amazoncorretto:21-alpine
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ms-account-domain.jar
ENTRYPOINT ["java", "-jar", "/app/ms-account-domain.jar"]