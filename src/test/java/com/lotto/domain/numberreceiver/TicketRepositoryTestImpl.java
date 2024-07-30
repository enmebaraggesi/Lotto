package com.lotto.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class TicketRepositoryTestImpl implements TicketRepository {
    
    Map<String, Ticket> inMemoryDatabase = new ConcurrentHashMap<>();
    
    @Override
    public Ticket save(final Ticket ticket) {
        inMemoryDatabase.put(ticket.id(), ticket);
        return ticket;
    }
    
    @Override
    public List<Ticket> findAllTicketsByDrawDate(final LocalDateTime date) {
        return inMemoryDatabase.values()
                               .stream()
                               .filter(ticket -> ticket.drawDate().equals(date))
                               .toList();
    }
}
