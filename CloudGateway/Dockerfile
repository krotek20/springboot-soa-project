# build docker image
# docker build -t soaproject/cloudgateway:latest .

# run docker image
# docker run -d -p9090:9090 -e CONFIG_SERVER_URL=host.docker.internal -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name cloudgateway 1660ba7c3990

FROM openjdk:17

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cloudgateway.jar
ENTRYPOINT ["java", "-jar", "/cloudgateway.jar"]

EXPOSE 9090