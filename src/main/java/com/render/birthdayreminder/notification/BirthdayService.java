package com.render.birthdayreminder.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BirthdayService {

    private final ReactiveStringRedisTemplate redisTemplate;

    public Mono<Boolean> saveToRedis(String name, String dob) {
        return redisTemplate.opsForValue().set(name, dob);
    }

    public Mono<List<String>> checkTodayBirthdays() {
        LocalDate today = LocalDate.now();
        return redisTemplate.keys("*")
                .flatMap(name -> redisTemplate.opsForValue().get(name)
                        .filter(dob -> dob.endsWith(today.toString().substring(5))) // match MM-DD
                        .map(dob -> name))
                .collectList();
    }
}
