package com.chenhz.exchange.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *描述:配置广播模式或者订阅模式队列
 * <p>
 * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
 *
 */
@Configuration
public class RabbitFanoutConfig {

    final static String PENGLEI = "fanout.penglei.net";

    final static String SOUYUNKU = "fanout.souyunku.com";

    @Bean
    public Queue queuePenglei(){
        return new Queue(RabbitFanoutConfig.PENGLEI);
    }

    @Bean
    public Queue queueSouyunku(){
        return new Queue(RabbitFanoutConfig.SOUYUNKU);
    }

    @Bean
    FanoutExchange fanoutExchange(){return new FanoutExchange("fanoutExchange");}

    @Bean
    Binding bindingExchangeQueuePenglei(Queue queuePenglei,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queuePenglei).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeQueueSouyunku(Queue queueSouyunku,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queueSouyunku).to(fanoutExchange);
    }

}
