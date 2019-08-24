//package com.chenhz.neo4j.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.neo4j.ogm.session.SessionFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
//import org.springframework.data.transaction.ChainedTransactionManager;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.TransactionManagementConfigurer;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//
//@Slf4j
//@Configuration
//public class TransactionConfig implements TransactionManagementConfigurer {
//
//
//    @Resource(name = "transactionManager")
//    private PlatformTransactionManager transactionManager;
//
//    @Resource(name = "neo4jTransactionManager")
//    private PlatformTransactionManager neo4jTransactionManager;
//
//
//
//    /**
//     * 在Spring容器中，我们手工注解@Bean 将被优先加载，框架不会重新实例化其他的 PlatformTransactionManager 实现类。
//     *
//     * @return
//     */
//    @Bean("transactionManager")
//    public PlatformTransactionManager transactionManager(DataSource dataSource) {
//        log.info(">>>>> 初始化 MySQL 事务管理器 <<<<<");
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean("neo4jTransactionManager")
//    public PlatformTransactionManager neo4jTransactionManager(SessionFactory sessionFactory) {
//        log.info(">>>>> 初始化 Neo4j 事务管理器 <<<<<");
//        return new Neo4jTransactionManager(sessionFactory);
//    }
//
//
//    /**
//     * 链式事务管理，弱一致性。 也可能出现 Neo4j 提交成功，而 Mysql 提交不成功的情况
//     * @return
//     */
//    @Bean("multiTransactionManager")
//    public PlatformTransactionManager multiTransactionManager(){
//        log.info(">>>>> 初始化 MySQL + Neo4j 事务管理器 <<<<<");
//        log.info(">>>>> neo4jTransactionManager"+ neo4jTransactionManager);
//        log.info(">>>>> transactionManager"+ transactionManager);
//        return new ChainedTransactionManager(neo4jTransactionManager,transactionManager);
//    }
//
//
//
//    /**
//     * 其返回值代表在拥有多个事务管理器的情况下默认使用的事务管理器
//     */
//    @Override
//    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        return transactionManager;
//    }
//}