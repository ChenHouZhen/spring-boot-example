package com.chenhz.neo4j.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class Config {


    @Bean
    public Object transactionManagerBean(PlatformTransactionManager platformTransactionManager){
        log.info(">>>>> 测试 Spring 框架注入的 事务管理器");
        log.info(">>>>> PlatformTransactionManager  ==> " + platformTransactionManager.getClass().getName());
        return new Object();
    }

}
