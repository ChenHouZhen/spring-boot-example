package com.chenhz.exchange.direct;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitDirectSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendHello(){
        String context = "此消息在，默认的交换机模式队列下，有 helloReceiver 可以收";

        String routeKey = "hello";

        context = "routeKey:" + routeKey + ",context:" + context;

        System.out.println("sendHello :" + context);

        this.amqpTemplate.convertAndSend(routeKey,context);
    }


    public void  sendDirect(){
        String context = "此消息在，默认的交换机模式队列下，有 DirectReceiver 可以收到";

        String routeKey = "direct";

        String exchange = "directExchange";

        context = "context:" + exchange +",routeKey:" + routeKey +",context:" + context;

        System.out.println("sendDirect :"+context);

        this.amqpTemplate.convertAndSend(routeKey,context);
    }
}
