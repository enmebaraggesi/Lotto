package com.lotto.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.Set;

record Ticket(String id, LocalDateTime drawDate, Set<Integer> userNumbers) {
    
}
