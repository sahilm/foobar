package com.sahilm.stuff;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;


class Producer {

    private static final String QUEUE_NAME = "hello";
    private final ConnectionFactory connectionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    public Producer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    void keepProducing() throws IOException, TimeoutException {
        final Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        System.out.println("Starting production in 500 ms...");
        executorService.scheduleAtFixedRate(() -> {
            try {
                channel.basicPublish("", QUEUE_NAME, null, atomicInteger.toString().getBytes(Charset.forName("UTF-8")));
                atomicInteger.incrementAndGet();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 500L, 500L, TimeUnit.MILLISECONDS);
    }
}
