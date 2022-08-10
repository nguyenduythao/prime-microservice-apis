# For Java 11, try this
FROM adoptopenjdk/openjdk11:alpine-jre

# Refer to Maven build -> finalName
ARG JAR_FILE=build/prime-api-gateway.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/*.jar /opt/app/prime-api-gateway.jar
COPY ${JAR_FILE} prime-api-gateway.jar

# java -jar /opt/app/prime-api-gateway.jar
ENTRYPOINT ["java","-jar","prime-api-gateway.jar"]