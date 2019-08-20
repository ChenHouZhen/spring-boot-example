package com.chenhz.exchange.topic;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitTopicSender {

    @Autowired
    private AmqpTemplate amqpTemplate;


    public void sendMessage(){

        String context = "此消息在，配置转发消息模式队列下， 有 TopicReceiver1 TopicReceiver2 TopicReceiver3 可以收到";

        String routeKey = "topic.message";

        String exchange = "topicExchange";

        context = "context:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        System.out.println("sendMessageTest : " + context);

        this.amqpTemplate.convertAndSend(exchange, routeKey, context);
    }

    public void sendMessages(){
        String context = "此消息在，配置转发消息模式队列下，有  TopicReceiver2 TopicReceiver3 可以收到";

        String routeKey = "topic.message.s";

        String exchange = "topicExchange";

        context = "context:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        System.out.println("sendMessagesTest : " + context);

        this.amqpTemplate.convertAndSend(exchange, routeKey, context);
    }

    public void sendYmq(){
        String context = "此消息在，配置转发消息模式队列下，有 TopicReceiver3 可以收到";

        String routeKey = "topic.ymq";

        String exchange = "topicExchange";

        context = "context:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        System.out.println("sendYmqTest : " + context);

        this.amqpTemplate.convertAndSend(exchange, routeKey, context);
    }
}
