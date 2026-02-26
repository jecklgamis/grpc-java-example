# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Run Commands

```bash
# Build (produces target/grpc-java-example.jar as a shaded/fat jar)
./mvnw clean package

# Run the server (port argument required)
java -jar target/grpc-java-example.jar 4000

# Run tests only
./mvnw test

# Run a single test class
./mvnw test -Dtest=ClassName

# Build and run via Docker
make up           # builds jar, builds image, runs container
make dist         # ./mvnw clean package
make image        # docker build
make run          # docker run -p 4000:4000
```

## Architecture

This is a minimal gRPC server example implementing an in-memory key-value store.

**Proto → Generated Code → Implementation flow:**
- `src/main/proto/kvstore.proto` defines the `KeyValueStore` service with two RPCs: `Get(Key) -> Value` and `Put(KeyValue) -> Empty`
- The `protobuf-maven-plugin` generates Java stubs from the proto file during `compile` phase into `target/generated-sources/`
- `InMemoryKeyValueStoreImpl` extends the generated `KeyValueStoreGrpc.KeyValueStoreImplBase` and holds state in a plain `HashMap<String, String>` (not thread-safe)
- `KeyValueStoreServer` is the entry point (`main`); it starts a Netty-backed gRPC server on the given port using `InsecureServerCredentials` (no TLS)

**Package:** `com.jecklgamis.grpc.java.example.kvstore`

**Key notes:**
- Java 21 is required (enforced by maven-enforcer-plugin), but the compiler source/target is set to 1.8
- The jar is built as a shaded (uber) jar via maven-shade-plugin with `KeyValueStoreServer` as the main class
- The server listens on port 4000 by default (hardcoded in `docker-entrypoint.sh`)
- CI runs `./mvnw --batch-mode verify` and pushes a Docker image to Docker Hub on merges to `main`
