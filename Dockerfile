FROM maven:3.8.6-openjdk-8-slim
WORKDIR /home
COPY . .
RUN mvn package -DskipTests # todo: use in memory database for tests
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/PruebaConcepto-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]