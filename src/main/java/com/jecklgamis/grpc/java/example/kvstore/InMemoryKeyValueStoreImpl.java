package com.jecklgamis.grpc.java.example.kvstore;

import io.grpc.stub.StreamObserver;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

class InMemoryKeyValueStoreImpl extends KeyValueStoreGrpc.KeyValueStoreImplBase {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(InMemoryKeyValueStoreImpl.class);
    private final Map<String, String> kvstore = new HashMap<>();

    @Override
    public void get(Key request, StreamObserver<Value> responseObserver) {
        LOGGER.info(String.format("GET : %s", request.getKey()));
        String v = kvstore.getOrDefault(request.getKey(), "");
        LOGGER.info(String.format("PUT : %s=%s", request.getKey(), v));
        Value value = Value.newBuilder().setValue(v).build();
        responseObserver.onNext(value);
        responseObserver.onCompleted();
    }

    @Override
    public void put(KeyValue request, StreamObserver<Empty> responseObserver) {
        LOGGER.info(String.format("PUT : %s=%s", request.getKey(), request.getValue()));
        kvstore.put(request.getKey(), request.getValue());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
