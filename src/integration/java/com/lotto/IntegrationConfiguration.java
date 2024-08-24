package com.lotto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Configuration
class IntegrationConfiguration {

    @Bean
    @Primary
    Clock clock() {
        return AdjustableClock.fixed(LocalDateTime.of(2024,8,16,12,0,0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
    }
}
