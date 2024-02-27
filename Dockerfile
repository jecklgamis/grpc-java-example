FROM ubuntu:22.04
MAINTAINER Jerrico Gamis <jecklgamis@gmail.com>

RUN apt update -y && apt install -y openjdk-21-jre-headless && apt clean all && rm -rf /var/lib/apt/lists/*

ENV APP_HOME /app
RUN groupadd -r app && useradd -r -gapp app
RUN mkdir -m 0755 -p ${APP_HOME}/bin

COPY target/grpc-java-example.jar ${APP_HOME}/bin
COPY docker-entrypoint.sh /

RUN chown -R app:app ${APP_HOME}

EXPOSE 4000

WORKDIR ${APP_HOME}
CMD ["/docker-entrypoint.sh"]

