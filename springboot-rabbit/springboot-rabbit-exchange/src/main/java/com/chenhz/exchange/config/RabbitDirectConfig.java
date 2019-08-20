package com.chenhz.exchange.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：配置默认的交换机模式
 *
 * Direct Exchange是RabbitMQ默认的交换机模式，也是最简单的模式，根据key全文匹配去寻找队列。
 */
@Configuration
public class RabbitDirectConfig {

    @Bean
    public Queue helloQueue(){
        return new Queue("hello");
    }

    @Bean
    public Queue directQueue(){
        return new Queue("direct");
    }

//    @Bean
//    public DirectExchange directExchange(){
//        return new DirectExchange("directExchange");
//    }
//
//    @Bean
//    public Binding bindingExchangeDirectQueue(Queue directQueue,DirectExchange directExchange){
//        return BindingBuilder.bind(directQueue).to(directExchange).with("direct");
//    }

}
