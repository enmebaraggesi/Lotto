package com.lotto.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryNumberReceiverRepositoryImpl implements NumberReceiverRepository {
    
    Map<String, Ticket> inMemoryDatabase = new ConcurrentHashMap<>();
    
    @Override
    public Ticket save(final Ticket ticket) {
        inMemoryDatabase.put(ticket.getId(), ticket);
        return ticket;
    }
    
    @Override
    public List<Ticket> findAllTicketsByDrawDate(final LocalDateTime date) {
        return inMemoryDatabase.values()
                               .stream()
                               .filter(ticket -> ticket.getDrawDate().equals(date))
                               .toList();
    }
}
