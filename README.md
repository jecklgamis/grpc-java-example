## grpc-java-example

[![Build](https://github.com/jecklgamis/grpc-java-example/actions/workflows/build.yml/badge.svg)](https://github.com/jecklgamis/grpc-java-example/actions/workflows/build.yml)

An example gRPC app using Java. The server is a simple key-value store.

Docker: `docker run -p 4000:4000 jecklgamis/grpc-java-example:main`

## Requirements

* Java 11
* GNU Make (optional)
* Docker (optional)

## Building

```
./mvnw clean package
```

## Running

```
java -jar target/grpc-java-example.jar 4000 
```

## Running Using Docker

Run `make up` or the following commands:

```
mvn clean package
docker build -t grpc-java-example:main .
docker run -p 4000 -it grpc-java-example:main .
```

