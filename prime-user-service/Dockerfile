# For Java 11, try this
FROM adoptopenjdk/openjdk11:alpine-jre

# Refer to Maven build -> finalName
ARG JAR_FILE=build/prime-user-service.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/*.jar /opt/app/prime-user-service.jar
COPY ${JAR_FILE} prime-user-service.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","prime-user-service.jar"]