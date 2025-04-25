package com.render.birthdayreminder.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/birthdays")
@RequiredArgsConstructor
public class BirthdayController {

    private final BirthdayService birthdayService;

    @PostMapping
    public Mono<ResponseEntity<Map<String, String>>> saveBirthday(@RequestBody BirthdayRequest request) {
        return birthdayService.saveToRedis(request.getName(), request.getDob())
                .map(success -> ResponseEntity.ok(Map.of("message", "Birthday saved!")));
    }
}
