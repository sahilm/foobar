package com.sahilm.stuff;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

class Consumer {

    private static final String QUEUE_NAME = "hello";
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");
    private final ConnectionFactory connectionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);


    Consumer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    void keepConsuming() throws IOException, TimeoutException {
        final Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        final com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(final String consumerTag, final Envelope envelope, final AMQP.BasicProperties
                    properties, final byte[] body) throws IOException {
                String message = new String(body, UTF_8_CHARSET);
                LOGGER.info(message);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
