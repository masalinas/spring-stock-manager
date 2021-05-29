FROM openjdk:11-oracle

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Xmx128m", "-jar", "app.jar"]
EXPOSE 8081