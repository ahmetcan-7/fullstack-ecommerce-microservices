FROM openjdk:11
EXPOSE 8084
COPY ./target/user-service-1.0-SNAPSHOT.jar user-service.jar
ENTRYPOINT ["java","-jar","/user-service.jar"]