package com.lotto.domain.resultannouncer;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record Response(String id,
                Set<Integer> numbers,
                Set<Integer> hitNumbers,
                LocalDateTime drawDate,
                boolean isWinner) {
    
}
