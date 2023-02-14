FROM gradle AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean bootJar --no-daemon

FROM openjdk:8-jre-slim as server
EXPOSE 8080
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/segment-generator-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/segment-generator-0.0.1-SNAPSHOT.jar"]