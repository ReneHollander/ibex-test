FROM node:8 AS builder
WORKDIR /root/
RUN npm set progress=false && npm config set depth 0 && npm cache clean --force
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

COPY .angular-cli.json tsconfig.json tslint.json ./
COPY src/main/typescript ./src/main/typescript
RUN echo "export const IBEX_BUILD_LONG_HASH = '$IBEX_BUILD_LONG_HASH';" > ./src/main/typescript/version.ts && \
echo "export const IBEX_BUILD_SHORT_HASH = '$IBEX_BUILD_SHORT_HASH';" >> ./src/main/typescript/version.ts && \
echo "export const IBEX_BUILD_BRANCH = '$IBEX_BUILD_BRANCH';" >> ./src/main/typescript/version.ts && \
echo "export const IBEX_BUILD_JOB_ID = $IBEX_BUILD_JOB_ID;" >> ./src/main/typescript/version.ts && \
echo "export const IBEX_BUILD_VERSION_TAG = '$IBEX_BUILD_VERSION_TAG';" >> ./src/main/typescript/version.ts && \
echo "export const IBEX_BUILD_TIME = '$IBEX_BUILD_TIME';" >> ./src/main/typescript/version.ts && \
echo "export const IBEX_BUILD_TIME_MILLIS = $IBEX_BUILD_TIME_MILLIS;" >> ./src/main/typescript/version.ts && \
npm run prod

FROM nginx:1.13-alpine
RUN rm -rf /usr/share/nginx/html/*
COPY nginx/default.conf /etc/nginx/conf.d/
COPY --from=builder /root/dist/public /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
