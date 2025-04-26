package com.render.birthdayreminder.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/birthdays")
@RequiredArgsConstructor
public class BirthdayController {

    private final ReactiveStringRedisTemplate redisTemplate;

    @PostMapping
    public Mono<ResponseEntity<String>> saveBirthday(@RequestBody BirthdayRequest request) {
        log.info("Saving birthday: Name={}, DOB={}", request.getName(), request.getDob());

        if (request.getName() == null || request.getDob() == null) {
            return Mono.just(ResponseEntity.badRequest().body("Name and DOB must not be null"));
        }

        return redisTemplate
                .opsForValue()
                .set(request.getName(), request.getDob())
                .map(success -> {
                    if (success) {
                        return ResponseEntity.ok("Birthday saved successfully!");
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save birthday");
                    }
                })
                .onErrorResume(e -> {
                    log.error("Redis error during saveBirthday", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Redis error: " + e.getMessage()));
                });
    }

    @GetMapping("/{name}")
    public Mono<ResponseEntity<String>> getBirthday(@PathVariable String name) {
        log.info("Fetching birthday for name: {}", name);

        return redisTemplate
                .opsForValue()
                .get(name)
                .map(dob -> ResponseEntity.ok("Birthday for " + name + " is: " + dob))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .onErrorResume(e -> {
                    log.error("Redis error during getBirthday", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Redis error: " + e.getMessage()));
                });
    }
}
