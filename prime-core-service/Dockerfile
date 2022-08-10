# For Java 11, try this
FROM adoptopenjdk/openjdk11:alpine-jre

# Refer to Maven build -> finalName
ARG JAR_FILE=build/prime-core-service.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/*.jar /opt/app/prime-core-service.jar
COPY ${JAR_FILE} prime-core-service.jar

# java -jar /opt/app/prime-core-service.jar
ENTRYPOINT ["java","-jar","prime-core-service.jar"]