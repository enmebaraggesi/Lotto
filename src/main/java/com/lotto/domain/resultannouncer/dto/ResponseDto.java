package com.lotto.domain.resultannouncer.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record ResponseDto(String id,
                          Set<Integer> numbers,
                          Set<Integer> hitNumbers,
                          LocalDateTime drawDate,
                          boolean isWinner) {
    
}
