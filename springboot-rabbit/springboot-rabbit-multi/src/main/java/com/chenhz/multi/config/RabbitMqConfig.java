package com.chenhz.multi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;

@Slf4j
@Configuration
public class RabbitMqConfig {


    @Primary
    @Bean("oneConnectionFactory")
    public ConnectionFactory oneConnectionFactory() {

        String host = "localhost";
        String username = "guest";
        String password = "guest";
        int port = 5672;

        log.info(" >>>>> 初始化 1 RabbitMQ <<<<<");
        log.info(" >>>>> host:{}",host);
        log.info(" >>>>> username:{}",username);
        log.info(" >>>>> password:{}",password);
        log.info(" >>>>> port:{}",port);

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPort(port);
        return connectionFactory;
    }

//    /**
//     * 声明RabbitAdmin
//     */
//    @Bean("twoRabbitAdmin")
//    public RabbitAdmin rabbitAdmin(@Qualifier("twoConnectionFactory") ConnectionFactory twoConnectionFactory){
//        return new RabbitAdmin(twoConnectionFactory);
//    }


    @Bean(name="oneContainerFactory")
    public SimpleRabbitListenerContainerFactory oneContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                                         @Qualifier("oneConnectionFactory") ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 手动 ACK
        // spring.rabbitmq.listener.simple.acknowledge-mode=manual
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean(name="twoRabbitTemplate")
    public RabbitTemplate secondRabbitTemplate(
            @Qualifier("twoConnectionFactory") ConnectionFactory connectionFactory){
        return new RabbitTemplate(connectionFactory);
    }


    @Bean(name="oneRabbitTemplate")
    @Primary // 这个 不一定需要
    public RabbitTemplate oneRabbitTemplate(
            @Qualifier("oneConnectionFactory") ConnectionFactory connectionFactory){
        return new RabbitTemplate(connectionFactory);
    }


    @Bean("twoConnectionFactory")
    public ConnectionFactory twoConnectionFactory() {

        String host = "10.0.130.141";
        String username = "admin";
        String password = "123456";
        int port = 5672;

        log.info(" >>>>> 初始化 2 RabbitMQ <<<<<");
        log.info(" >>>>> host:{}",host);
        log.info(" >>>>> username:{}",username);
        log.info(" >>>>> password:{}",password);
        log.info(" >>>>> port:{}",port);

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPort(port);

        // 第一个参数： 是否持久化
        // 第二个参数： 仅创建者可以使用的私有队列，断开后自动删除
        // 第三个参数： 当所有消费客户端连接断开后，是否自动删除队列
        try {
            connectionFactory.createConnection().createChannel(false).queueDeclare("HELLO-2", true, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connectionFactory;
    }


    @Bean(name="twoContainerFactory")
    public SimpleRabbitListenerContainerFactory twoContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                                         @Qualifier("twoConnectionFactory") ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 手动 ACK
        // spring.rabbitmq.listener.simple.acknowledge-mode=manual
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public Queue oneQueue() {
        log.info(">>>>> config queue HELLO-1 <<<<<");
        return new Queue("HELLO-1");
    }

}
