package com.lotto.domain.numberreceiver;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class TicketRepositoryTestImpl implements TicketRepository {
    
    Map<String, Ticket> inMemoryDatabase = new ConcurrentHashMap<>();
    
    @Override
    public Ticket save(final Ticket ticket) {
        String id = UUID.randomUUID().toString();
        Ticket toSave = Ticket.builder()
                             .id(id)
                             .userNumbers(ticket.userNumbers())
                             .drawDate(ticket.drawDate())
                             .build();
        inMemoryDatabase.put(id, toSave);
        return toSave;
    }
    
    @Override
    public List<Ticket> findAllTicketsByDrawDate(final LocalDateTime date) {
        return inMemoryDatabase.values()
                               .stream()
                               .filter(ticket -> ticket.drawDate().equals(date))
                               .toList();
    }
    
    @Override
    public <S extends Ticket> S insert(final S entity) {
        return null;
    }
    
    @Override
    public <S extends Ticket> List<S> insert(final Iterable<S> entities) {
        return List.of();
    }
    
    @Override
    public <S extends Ticket> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }
    
    @Override
    public <S extends Ticket> List<S> findAll(final Example<S> example) {
        return List.of();
    }
    
    @Override
    public <S extends Ticket> List<S> findAll(final Example<S> example, final Sort sort) {
        return List.of();
    }
    
    @Override
    public <S extends Ticket> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }
    
    @Override
    public <S extends Ticket> long count(final Example<S> example) {
        return 0;
    }
    
    @Override
    public <S extends Ticket> boolean exists(final Example<S> example) {
        return false;
    }
    
    @Override
    public <S extends Ticket, R> R findBy(final Example<S> example, final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
    
    @Override
    public <S extends Ticket> List<S> saveAll(final Iterable<S> entities) {
        return List.of();
    }
    
    @Override
    public Optional<Ticket> findById(final String string) {
        return Optional.empty();
    }
    
    @Override
    public boolean existsById(final String string) {
        return false;
    }
    
    @Override
    public List<Ticket> findAll() {
        return List.of();
    }
    
    @Override
    public List<Ticket> findAllById(final Iterable<String> strings) {
        return List.of();
    }
    
    @Override
    public long count() {
        return 0;
    }
    
    @Override
    public void deleteById(final String string) {
    
    }
    
    @Override
    public void delete(final Ticket entity) {
    
    }
    
    @Override
    public void deleteAllById(final Iterable<? extends String> strings) {
    
    }
    
    @Override
    public void deleteAll(final Iterable<? extends Ticket> entities) {
    
    }
    
    @Override
    public void deleteAll() {
    
    }
    
    @Override
    public List<Ticket> findAll(final Sort sort) {
        return List.of();
    }
    
    @Override
    public Page<Ticket> findAll(final Pageable pageable) {
        return null;
    }
}
