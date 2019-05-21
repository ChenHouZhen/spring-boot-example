package com.chenhz.session.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class SessionConfig {

    @Bean
    public RedisHttpSessionConfiguration redisHttpSessionConfiguration(DefaultCookieSerializer myCookieSerializer) {
        RedisHttpSessionConfiguration redisHttpSessionConfiguration = new RedisHttpSessionConfiguration();
        redisHttpSessionConfiguration.setCookieSerializer(myCookieSerializer);
        redisHttpSessionConfiguration.setMaxInactiveIntervalInSeconds(3600);
        return redisHttpSessionConfiguration;
    }

    @Bean
    public DefaultCookieSerializer myCookieSerializer() {
        DefaultCookieSerializer myCookieSerializer = new DefaultCookieSerializer();
        myCookieSerializer.setCookieName("session_id");
        myCookieSerializer.setUseHttpOnlyCookie(true);
        return myCookieSerializer;
    }
}
