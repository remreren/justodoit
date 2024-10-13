FROM maven:3.9.9-eclipse-temurin-21-alpine as build
WORKDIR /app
COPY . .

RUN --mount=type=cache,target=/root/.m2 mvn -f /app/pom.xml -B -U clean package -DskipTests=true -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn

FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=build /app/target/justodoit*.jar /justodoit.jar

ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:+UseStringDeduplication", "-jar", "/justodoit.jar"]