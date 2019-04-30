package com.chenhz.view;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// 配置 Mapper 的扫描路径，也可以不配置这个，在每个Mapper上加 @Mapper 也可以
@MapperScan(basePackages={"com.chenhz.view.dao"})
public class SpringbootViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootViewApplication.class, args);
    }

}
