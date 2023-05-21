FROM gradle:jdk17-alpine as build
WORKDIR /opt/app
COPY . .
RUN gradle build

FROM openjdk:17-jdk-slim
WORKDIR /opt/app
COPY --from=0 /opt/app/build/libs/spotify-shuffle-0.1.0.jar ./
CMD ["java", "-jar", "spotify-shuffle-0.1.0.jar"]