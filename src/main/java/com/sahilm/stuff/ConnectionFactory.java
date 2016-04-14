package com.sahilm.stuff;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class ConnectionFactory {

    private final com.rabbitmq.client.ConnectionFactory factory;

    ConnectionFactory(final String host) {
        factory = new com.rabbitmq.client.ConnectionFactory();
        factory.setHost(host);
    }

    Connection newConnection() throws IOException, TimeoutException {
        return factory.newConnection();
    }
}
