package com.sahilm.example1;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

class Recv {
    private static final String QUEUE_NAME = "hello";
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    public static void main(String[] args) throws IOException, TimeoutException {
        final Properties properties = new Properties();
        properties.load(Recv.class.getClassLoader().getResourceAsStream("app.properties"));

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(properties.getProperty("host"));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(final String consumerTag, final Envelope envelope, final AMQP.BasicProperties
                    properties, final byte[] body) throws IOException {
                String message = new String(body, UTF_8_CHARSET);
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
