package com.chenhz.neo4j;

import com.chenhz.common.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Import(SwaggerConfig.class)
@SpringBootApplication
public class SpringbootNeo4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootNeo4jApplication.class, args);
    }

}
