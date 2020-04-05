FROM node:12.16.1-stretch AS builder
WORKDIR /root/
RUN apt-get update -qqyy && apt-get dist-upgrade -qqyy && apt-get install -qqyy zopfli && rm -rf /var/lib/apt/lists/* && npm set progress=false && npm config set depth 0 && npm cache clean --force
COPY package.json package-lock.json ./
RUN npm install

ARG IBEX_BUILD_LONG_HASH=0000000000000000000000000000000000000000
ARG IBEX_BUILD_SHORT_HASH=00000000
ARG IBEX_BUILD_BRANCH=no_branch_supplied
ARG IBEX_BUILD_JOB_ID=0
ARG IBEX_BUILD_VERSION_TAG=00000000-0
ARG IBEX_BUILD_TIME=1970-01-01T00:00:00Z
ARG IBEX_BUILD_TIME_MILLIS=0

ENV IBEX_BUILD_LONG_HASH=$IBEX_BUILD_LONG_HASH IBEX_BUILD_SHORT_HASH=$IBEX_BUILD_SHORT_HASH IBEX_BUILD_BRANCH=$IBEX_BUILD_BRANCH IBEX_BUILD_JOB_ID=$IBEX_BUILD_JOB_ID IBEX_BUILD_VERSION_TAG=$IBEX_BUILD_VERSION_TAG IBEX_BUILD_TIME=$IBEX_BUILD_TIME IBEX_BUILD_TIME_MILLIS=$IBEX_BUILD_TIME_MILLIS

COPY angular.json tsconfig.json tslint.json ./
COPY src/main/typescript ./src/main/typescript
RUN echo "export const IBEX_BUILD_LONG_HASH = '$IBEX_BUILD_LONG_HASH';" > ./src/main/typescript/version.ts && \
    echo "export const IBEX_BUILD_SHORT_HASH = '$IBEX_BUILD_SHORT_HASH';" >> ./src/main/typescript/version.ts && \
    echo "export const IBEX_BUILD_BRANCH = '$IBEX_BUILD_BRANCH';" >> ./src/main/typescript/version.ts && \
    echo "export const IBEX_BUILD_JOB_ID = $IBEX_BUILD_JOB_ID;" >> ./src/main/typescript/version.ts && \
    echo "export const IBEX_BUILD_VERSION_TAG = '$IBEX_BUILD_VERSION_TAG';" >> ./src/main/typescript/version.ts && \
    echo "export const IBEX_BUILD_TIME = '$IBEX_BUILD_TIME';" >> ./src/main/typescript/version.ts && \
    echo "export const IBEX_BUILD_TIME_MILLIS = $IBEX_BUILD_TIME_MILLIS;" >> ./src/main/typescript/version.ts && \
    npm run prod && \
    find /root/dist/ -type f -regextype posix-extended -iregex '.*\.(css|html|js|svg|txt|eot|ico)' -exec zopfli -i1000 --gzip '{}' \;

FROM nginx:1.17.9-alpine
RUN rm -rf /etc/nginx/conf.d/ /usr/share/nginx/html/*
COPY nginx/nginx.conf /etc/nginx/nginx.conf
COPY deployment/frontend/start.sh /start.sh
RUN chmod +x /start.sh
COPY --from=builder /root/dist/public /usr/share/nginx/html
CMD ["/bin/sh", "/start.sh"]
