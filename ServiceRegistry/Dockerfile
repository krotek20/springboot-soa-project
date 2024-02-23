# Build docker image
# docker build -t soaproject/serviceregistry:0.0.1 .

# Run docker image
# docker run -d -p8761:8761 --name serviceregistry 3e4729b01b8f

FROM openjdk:17

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} serviceregistry.jar
ENTRYPOINT ["java", "-jar", "/serviceregistry.jar"]

EXPOSE 8761