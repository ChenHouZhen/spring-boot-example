package com.chenhz.ack.ack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitAckSender implements RabbitTemplate.ReturnCallback,RabbitTemplate.ConfirmCallback {

    private Logger logger = LoggerFactory.getLogger(RabbitAckSender.class);

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String context){

//        connectionFactory.setPublisherConfirms(true);
//        connectionFactory.setPublisherReturns(true);
//        // 消息发送失败返回到队列中, application.properties 配置 spring.rabbitmq.publisher-returns=true
//        rabbitTemplate = new RabbitTemplate(connectionFactory);
//
//        rabbitTemplate.setMandatory(true);
//
//        rabbitTemplate.setReturnCallback(this);

//        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause)->{
//            if (!ack){
//                logger.info("发送失败："+ cause + correlationData.toString());
//            } else {
//                logger.info("发送成功");
//            }
//        });


//        this.rabbitTemplate.setConfirmCallback(this);
//
        logger.info("HelloSender 发送的消息内容：{}", context);

        this.rabbitTemplate.convertAndSend("ack", context);

    }

    /**
     * 实现ConfirmCallback
     * <p>
     * ACK=true仅仅标示消息已被Broker接收到，并不表示已成功投放至消息队列中
     * ACK=false标示消息由于Broker处理错误，消息并未处理成功
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
//        logger.info("消息id: " + correlationData + "确认" + (ack ? "成功:" : "失败"));
    }


    /**
     * 失败后返回消息回调
     * <p>
     * 当消息发送出去找不到对应路由队列时，将会把消息退回
     * 如果有任何一个路由队列接收投递消息成功，则不会退回消息
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//        logger.info("return--message:" + new String(message.getBody()) + ",replyCode:" + replyCode + ",replyText:" + replyText + ",exchange:" + exchange + ",routingKey:" + routingKey);

    }
}
