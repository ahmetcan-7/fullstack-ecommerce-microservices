FROM openjdk:11
EXPOSE 8761
COPY ./target/eureka-server-1.0-SNAPSHOT.jar eureka-server.jar
ENTRYPOINT ["java","-jar","/eureka-server.jar"]