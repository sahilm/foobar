package com.sahilm.example1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

class Send {
    private final static String QUEUE_NAME = "hello";
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    public static void main(String[] args) throws IOException, TimeoutException {
        final Properties properties = new Properties();
        properties.load(Send.class.getClassLoader().getResourceAsStream("app.properties"));
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(properties.getProperty("host"));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";

        channel.basicPublish("", QUEUE_NAME, null, message.getBytes(UTF_8_CHARSET));
        System.out.println(" [x] Sent '" + message + "'");
    }
}
