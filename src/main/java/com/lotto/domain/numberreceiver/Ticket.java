package com.lotto.domain.numberreceiver;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
class Ticket {
    
    private final String id;
    private final LocalDateTime drawDate;
    private final Set<Integer> userNumbers;
    
    Ticket(final String id, final LocalDateTime drawDate, final Set<Integer> userNumbers) {
        this.id = id;
        this.drawDate = drawDate;
        this.userNumbers = userNumbers;
    }
}
