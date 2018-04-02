FROM openjdk:8-jdk AS builder
WORKDIR /root/
COPY gradlew ./
COPY gradle ./gradle
RUN chmod +x ./gradlew && ./gradlew --no-daemon

COPY settings.gradle build.gradle ./
RUN ./gradlew downloadRepos --no-daemon

ARG IBEX_BUILD_LONG_HASH=0000000000000000000000000000000000000000
ARG IBEX_BUILD_SHORT_HASH=00000000
ARG IBEX_BUILD_BRANCH=no_branch_supplied
ARG IBEX_BUILD_JOB_ID=0
ARG IBEX_BUILD_VERSION_TAG=00000000-0
ARG IBEX_BUILD_TIME=1970-01-01T00:00:00Z
ARG IBEX_BUILD_TIME_MILLIS=0

ENV IBEX_BUILD_LONG_HASH=$IBEX_BUILD_LONG_HASH IBEX_BUILD_SHORT_HASH=$IBEX_BUILD_SHORT_HASH IBEX_BUILD_BRANCH=$IBEX_BUILD_BRANCH IBEX_BUILD_JOB_ID=$IBEX_BUILD_JOB_ID IBEX_BUILD_VERSION_TAG=$IBEX_BUILD_VERSION_TAG IBEX_BUILD_TIME=$IBEX_BUILD_TIME IBEX_BUILD_TIME_MILLIS=$IBEX_BUILD_TIME_MILLIS

COPY src/main/resources ./src/main/resources
COPY src/test/java ./src/test/java
COPY src/main/java ./src/main/java
RUN ./gradlew build --no-daemon -Pspeed=true

FROM openjdk:8-jre-slim
WORKDIR /root/
COPY --from=builder /root/build/libs/ibex.jar ./ibex.jar
EXPOSE 8080
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "./ibex.jar"]
