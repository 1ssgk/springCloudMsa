FROM gradle:7.6 as builder
WORKDIR /build
COPY gradlew gradle build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

COPY . /build
RUN gradle build -x test --parallel



FROM openjdk:11
WORKDIR /app
VOLUME /tmp

#COPY build/libs/subject-1.0.jar app.jar

COPY --from=builder /build/build/libs/eurekaserver-0.0.1-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java","-jar","app.jar"]
