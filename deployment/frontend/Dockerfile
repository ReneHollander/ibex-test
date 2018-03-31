FROM openjdk:8-jdk AS builder
WORKDIR /root/
COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle
RUN chmod +x ./gradlew && ./gradlew

RUN curl -sL https://deb.nodesource.com/setup_8.x | bash - && apt-get update -qqy && apt-get -qqyy install nodejs && rm -rf /var/lib/apt/lists/* && npm set progress=false && npm config set depth 0 && npm cache clean --force
COPY package.json package-lock.json ./
RUN npm install
COPY .angular-cli.json tsconfig.json tslint.json ./
COPY src ./src
RUN ./gradlew frontendInjectVersion && npm run prod

FROM nginx:1.13-alpine
RUN rm -rf /usr/share/nginx/html/*
COPY nginx/default.conf /etc/nginx/conf.d/
COPY --from=builder /root/dist/public /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
