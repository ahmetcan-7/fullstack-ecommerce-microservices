FROM openjdk:11
EXPOSE 8889
COPY ./target/gateway-1.0-SNAPSHOT.jar api-gateway.jar
ENTRYPOINT ["java","-jar","/api-gateway.jar"]