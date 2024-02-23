# build docker image
# docker build -t soaproject/orderservice:latest .

# run docker image
# docker run -d -p8081:8081 -e CONFIG_SERVER_URL=host.docker.internal -e DB_HOST=host.docker.internal -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name orderservice 067b1602e3df

FROM openjdk:17

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} orderservice.jar
ENTRYPOINT ["java", "-jar", "/orderservice.jar"]

EXPOSE 8081