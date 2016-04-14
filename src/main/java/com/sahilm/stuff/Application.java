package com.sahilm.stuff;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class Application {
    public static void main(String[] args) throws IOException, TimeoutException {
        AbstractApplicationContext ctx =
                new ClassPathXmlApplicationContext("context.xml");

        ctx.getBean(Consumer.class).keepConsuming();
        ctx.getBean(Producer.class).keepProducing();

    }
}
