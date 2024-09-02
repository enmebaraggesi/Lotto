package com.lotto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@Configuration
class IntegrationConfiguration {
    
    @Bean
    @Primary
    AdjustableClock clock() {
        return AdjustableClock.ofLocalDateAndLocalTime(LocalDate.of(2024, 8, 16), LocalTime.of(12, 0, 0), ZoneId.systemDefault());
    }
}
