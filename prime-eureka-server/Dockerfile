# For Java 11, try this
FROM adoptopenjdk/openjdk11:alpine-jre

# Refer to Maven build -> finalName
ARG JAR_FILE=build/prime-eureka-server.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/*.jar /opt/app/prime-eureka-server.jar
COPY ${JAR_FILE} prime-eureka-server.jar

# java -jar /opt/app/prime-eureka-server.jar
ENTRYPOINT ["java","-jar","prime-eureka-server.jar"]