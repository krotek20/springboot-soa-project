# build docker image
# docker build -t soaproject/paymentservice:latest .

# run docker image
# docker run -d -p8083:8083 -e CONFIG_SERVER_URL=host.docker.internal -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name paymentservice 1660ba7c3990

FROM openjdk:17

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} paymentservice.jar
ENTRYPOINT ["java", "-jar", "/paymentservice.jar"]

EXPOSE 8083