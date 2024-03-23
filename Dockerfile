FROM gradle:8.4.0-jdk17-alpine AS build
COPY --chown=gradle:gradle .. /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

FROM openjdk:17-jdk-alpine as prod
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=build /home/gradle/src/build/libs/*.jar ./app.jar
EXPOSE 8080
CMD ["java","-jar","app.jar"]