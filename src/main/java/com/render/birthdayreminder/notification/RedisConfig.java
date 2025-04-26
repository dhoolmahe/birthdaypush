package com.render.birthdayreminder.notification;

import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class RedisConfig {


    @Value("${redis.url}")
    private String redisUrl;

    @Bean
    public ReactiveRedisConnectionFactory redisConnectionFactory() {
        RedisURI uri = RedisURI.create(redisUrl);
        return new LettuceConnectionFactory((RedisConfiguration) uri);
    }

//    @Bean
//    public ReactiveStringRedisTemplate redisTemplate(ReactiveRedisConnectionFactory factory) {
//        return new ReactiveStringRedisTemplate(factory);
//    }

    @Bean
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisTemplate<>(factory, RedisSerializationContext.string());
    }
}

