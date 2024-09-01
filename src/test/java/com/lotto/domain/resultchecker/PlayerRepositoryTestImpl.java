package com.lotto.domain.resultchecker;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class PlayerRepositoryTestImpl implements PlayerLotsRepository {
    
    private final Map<String, PlayerLot> inMemoryDatabase = new ConcurrentHashMap<>();
    
    @Override
    public PlayerLot save(final PlayerLot playerLot) {
        inMemoryDatabase.put(playerLot.id(), playerLot);
        return playerLot;
    }
    
    @Override
    public <S extends PlayerLot> List<S> saveAll(final Iterable<S> entities) {
        entities.forEach(playerLot -> {
            inMemoryDatabase.put(playerLot.id(), playerLot);
        });
        return List.of(entities.iterator().next());
    }
    
    @Override
    public Optional<PlayerLot> findById(final String id) {
        return Optional.ofNullable(inMemoryDatabase.get(id));
    }
    
    @Override
    public boolean existsById(final String string) {
        return false;
    }
    
    @Override
    public <S extends PlayerLot> S insert(final S entity) {
        return null;
    }
    
    @Override
    public <S extends PlayerLot> List<S> insert(final Iterable<S> entities) {
        return List.of();
    }
    
    @Override
    public <S extends PlayerLot> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }
    
    @Override
    public <S extends PlayerLot> List<S> findAll(final Example<S> example) {
        return List.of();
    }
    
    @Override
    public <S extends PlayerLot> List<S> findAll(final Example<S> example, final Sort sort) {
        return List.of();
    }
    
    @Override
    public <S extends PlayerLot> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }
    
    @Override
    public <S extends PlayerLot> long count(final Example<S> example) {
        return 0;
    }
    
    @Override
    public <S extends PlayerLot> boolean exists(final Example<S> example) {
        return false;
    }
    
    @Override
    public <S extends PlayerLot, R> R findBy(final Example<S> example, final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
    
    @Override
    public List<PlayerLot> findAll() {
        return List.of();
    }
    
    @Override
    public List<PlayerLot> findAllById(final Iterable<String> strings) {
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
    public void delete(final PlayerLot entity) {
    
    }
    
    @Override
    public void deleteAllById(final Iterable<? extends String> strings) {
    
    }
    
    @Override
    public void deleteAll(final Iterable<? extends PlayerLot> entities) {
    
    }
    
    @Override
    public void deleteAll() {
    
    }
    
    @Override
    public List<PlayerLot> findAll(final Sort sort) {
        return List.of();
    }
    
    @Override
    public Page<PlayerLot> findAll(final Pageable pageable) {
        return null;
    }
}
