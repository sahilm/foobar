package com.sahilm.stuff;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class Application {
    public static void main(String[] args) throws IOException, TimeoutException {
        AbstractApplicationContext ctx =
                new ClassPathXmlApplicationContext("context.xml");

        final Producer bean = ctx.getBean(Producer.class);
        bean.keepProducing();

        final Consumer consumer = ctx.getBean(Consumer.class);
        consumer.keepConsuming();
    }
}
