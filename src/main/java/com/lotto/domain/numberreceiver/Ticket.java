package com.lotto.domain.numberreceiver;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record Ticket(String id, LocalDateTime drawDate, Set<Integer> userNumbers) {
    
}
