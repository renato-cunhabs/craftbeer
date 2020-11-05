FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} craft-beer.jar
ENTRYPOINT ["java","-jar","/craft-beer.jar"]