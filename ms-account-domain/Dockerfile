FROM amazoncorretto:21
LABEL MAINTAINER="jeanpiermendoza@outlook.com"
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ms-account-domain.jar
ENTRYPOINT ["java", "-jar", "/app/ms-account-domain.jar"]