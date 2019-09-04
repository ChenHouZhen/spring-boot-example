package com.chenhz.multi.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "HELLO-2", containerFactory="twoContainerFactory" )
public class TwoReceiver {


    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver Hello-2: " + hello);
    }
}
