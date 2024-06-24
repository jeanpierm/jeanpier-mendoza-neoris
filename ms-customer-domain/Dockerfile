FROM amazoncorretto:21
LABEL MAINTAINER="jeanpiermendoza@outlook.com"
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ms-customer-domain.jar
ENTRYPOINT ["java", "-jar", "/app/ms-customer-domain.jar"]