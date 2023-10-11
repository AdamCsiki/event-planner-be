FROM openjdk:17-jdk-alpine
MAINTAINER CsikiAdam
COPY target/ws-springboot-project-planner.jar projectplanner-server.jar
ENTRYPOINT ["java", "-jar", "/springboot-projectplanner-server.jar"]
