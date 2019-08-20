package com.chenhz.ack.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitDirectAckConfig {

    @Bean
    public Queue queueA(){
        // boolean durable = true 持久化
        return new Queue("ack",true);
    }
}
