FROM openjdk:11-jdk-slim-buster AS builder

WORKDIR /home/builder

COPY . .

ENV GENERATE_SOURCEMAP 'false'
ENV APP_ENV 'production'

RUN bash ./install.sh && ./gradlew build

# https://pkgs.alpinelinux.org/package/edge/community/x86_64/openjdk11
FROM alpine:3.15.0

RUN apk add openjdk11 \
  && adduser \
  --disabled-password \
  --home /home/app \
  --gecos '' app \
  && chown -R app /home/app

USER app

WORKDIR /home/app

COPY --from=builder /home/builder/build/libs/ocho-0.0.1-SNAPSHOT.jar .

CMD [ "java","-jar","ocho-0.0.1-SNAPSHOT.jar" ]