package com.lotto.domain.numberreceiver;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
class Ticket {
    
    String id;
    LocalDateTime drawDate;
    Set<Integer> userNumbers;
    
    Ticket(final String id, final LocalDateTime drawDate, final Set<Integer> userNumbers) {
        this.id = id;
        this.drawDate = drawDate;
        this.userNumbers = userNumbers;
    }
}
