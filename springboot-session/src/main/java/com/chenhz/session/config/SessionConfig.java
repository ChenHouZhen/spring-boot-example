package com.chenhz.session.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class SessionConfig {

    @Bean
    public RedisHttpSessionConfiguration redisHttpSessionConfiguration(DefaultCookieSerializer myCookieSerializer,Jackson2JsonRedisSerializer jackson2JsonRedisSerializer) {
        RedisHttpSessionConfiguration redisHttpSessionConfiguration = new RedisHttpSessionConfiguration();
        redisHttpSessionConfiguration.setCookieSerializer(myCookieSerializer);
        redisHttpSessionConfiguration.setMaxInactiveIntervalInSeconds(3600);
        redisHttpSessionConfiguration.setDefaultRedisSerializer(jackson2JsonRedisSerializer);
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
