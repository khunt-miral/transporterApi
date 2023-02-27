FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 9090
VOLUME /tmp
ADD target/TransporterAPI-0.0.1-SNAPSHOT.jar TransporterAPI-0.0.1-SNAPSHOT.jar
ADD target/envProd.properties envProd.properties
ENTRYPOINT ["java","-jar","/TransporterAPI-0.0.1-SNAPSHOT.jar","--spring.config.import=envProd.properties"]