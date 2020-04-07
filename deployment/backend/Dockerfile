FROM adoptopenjdk/openjdk13:debian AS builder
WORKDIR /root/
COPY gradlew ./
COPY gradle ./gradle
RUN chmod +x ./gradlew

COPY settings.gradle build.gradle ./

ARG IBEX_BUILD_GITHUB_SHA=0000000000000000000000000000000000000000
ARG IBEX_BUILD_GITHUB_REF=no_ref_specified
ARG IBEX_BUILD_GITHUB_TAG=no_tag_specified
ARG IBEX_BUILD_GITHUB_RUN_ID=0
ARG IBEX_BUILD_GITHUB_RUN_NUMBER=0

ENV IBEX_BUILD_GITHUB_SHA=$IBEX_BUILD_GITHUB_SHA IBEX_BUILD_GITHUB_REF=$IBEX_BUILD_GITHUB_REF IBEX_BUILD_GITHUB_TAG=$IBEX_BUILD_GITHUB_TAG IBEX_BUILD_GITHUB_RUN_ID=$IBEX_BUILD_GITHUB_RUN_ID IBEX_BUILD_GITHUB_RUN_NUMBER=$IBEX_BUILD_GITHUB_RUN_NUMBER

COPY src/main/resources ./src/main/resources
COPY src/test/java ./src/test/java
COPY src/main/java ./src/main/java
RUN ./gradlew build

FROM adoptopenjdk/openjdk13:alpine-jre
WORKDIR /root/
COPY --from=builder /root/build/libs/ibex.jar ./ibex.jar
EXPOSE 8080
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "./ibex.jar"]
