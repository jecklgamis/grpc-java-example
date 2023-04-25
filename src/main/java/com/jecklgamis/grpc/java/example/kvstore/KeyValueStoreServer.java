package com.jecklgamis.grpc.java.example.kvstore;


import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;


import java.io.IOException;

public class KeyValueStoreServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyValueStoreServer.class);
    private Server server;
    private int port;

    public KeyValueStoreServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        int port = 4000;
        server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new InMemoryKeyValueStoreImpl())
                .build()
                .start();
        LOGGER.info("gRPC Server started on port " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                KeyValueStoreServer.this.stop();
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
            LOGGER.warn("gRPC Shutting down");
        }));
    }

    private void awaitTermination() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 1) {
            LOGGER.info("Requires port number");
            System.exit(0);
        }
        KeyValueStoreServer server = new KeyValueStoreServer(Integer.parseInt(args[0]));
        server.start();
        server.awaitTermination();
    }
}

