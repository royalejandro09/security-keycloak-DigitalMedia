FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY ./target/eureka-service-0.0.1-SNAPSHOT.jar .
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eureka-service-0.0.1-SNAPSHOT.jar"]



