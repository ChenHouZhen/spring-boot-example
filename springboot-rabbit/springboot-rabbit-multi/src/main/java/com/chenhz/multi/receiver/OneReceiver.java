package com.chenhz.multi.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "HELLO-1", containerFactory="oneContainerFactory" )
public class OneReceiver {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver Hello-1: " + hello);
    }

}
