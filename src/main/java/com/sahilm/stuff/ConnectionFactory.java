package com.sahilm.stuff;

import com.google.common.io.Files;
import com.rabbitmq.client.Connection;
import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class ConnectionFactory {

    private final com.rabbitmq.client.ConnectionFactory factory;

    ConnectionFactory() throws Exception {
        setupBroker();
        factory = new com.rabbitmq.client.ConnectionFactory();
        factory.setUri("amqp://guest:password@127.0.0.1:5672");
        factory.useSslProtocol();
    }

    private void setupBroker() throws Exception {
        final Broker broker = new Broker();
        final BrokerOptions brokerOptions = new BrokerOptions();
        final String amqpPort = "5672";
        final String httpPort = "15672";
        final String qpidTempdir = Files.createTempDir().getAbsolutePath();
        final String qpidHomeDir = new ClassPathResource("qpid").getFile().getAbsolutePath();
        final String qpidConfigFileName = "/config.json";

        System.out.println(" qpid home dir=" + qpidHomeDir);
        System.out.println(" qpid work dir=" + qpidTempdir);

        brokerOptions.setConfigProperty("qpid.work_dir", qpidTempdir);

        brokerOptions.setConfigProperty("qpid.amqp_port", amqpPort);
        brokerOptions.setConfigProperty("qpid.http_port", httpPort);
        brokerOptions.setConfigProperty("qpid.home_dir", qpidHomeDir);
        brokerOptions.setInitialConfigurationLocation(qpidHomeDir + qpidConfigFileName);
        broker.startup(brokerOptions);
        System.out.println("broker started");
    }

    Connection newConnection() throws IOException, TimeoutException {
        return factory.newConnection();
    }
}
