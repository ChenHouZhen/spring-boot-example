package com.chenhz.exchange.fanout;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitFanoutSender {

    @Autowired
    private AmqpTemplate amqpTemplate;


    public void sendPenglei(){
        String context = "此消息在，广播模式或者订阅模式队列下，有 FanoutReceiver1 FanoutReceiver2 可以收到";

        String routeKey = "topic.penglei.net";

        String exchange = "fanoutExchange";

        System.out.println("sendPengleiTest : " + context);

        context = "context:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        this.amqpTemplate.convertAndSend(exchange,routeKey,context);
    }

    public void sendSouyunku(){

        String context = "此消息在，广播模式或者订阅模式队列下，有 FanoutReceiver1 FanoutReceiver2 可以收到";

        String routeKey = "topic.souyunku.com";

        String exchange = "fanoutExchange";

        context = "context:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        System.out.println("sendSouyunkuTest : " + context);

        this.amqpTemplate.convertAndSend(exchange,routeKey,context);
    }
}
