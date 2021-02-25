FROM adoptopenjdk/openjdk11

RUN apt-get update -y && apt-get install maven -y

COPY . /app
WORKDIR /app

RUN mvn -v
RUN mvn clean verify -Pfailsafe
EXPOSE 8080

ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=dev", "target/manager-api-0.0.1-SNAPSHOT.jar"]