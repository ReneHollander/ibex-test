FROM openjdk:8-jdk AS builder
WORKDIR /root/
COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle
RUN ./gradlew

# download dependencies
RUN ./gradlew build -x :bootJar -x test --continue
COPY src ./src
RUN ./gradlew build

FROM openjdk:8-jre-slim
COPY --from=builder /root/build/libs/ibex.jar ibex.jar
EXPOSE 8080
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "ibex.jar"]
