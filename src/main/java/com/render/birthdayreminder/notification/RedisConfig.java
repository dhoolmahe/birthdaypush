package com.render.birthdayreminder.notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("your-upstash-host", 6379);
    }

    @Bean
    public ReactiveStringRedisTemplate redisTemplate(ReactiveRedisConnectionFactory factory) {
        return new ReactiveStringRedisTemplate(factory);
    }
}

