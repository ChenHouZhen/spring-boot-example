package com.chenhz.session;

import com.chenhz.common.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SwaggerConfig.class)
public class SpringbootSessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSessionApplication.class, args);
    }

}
