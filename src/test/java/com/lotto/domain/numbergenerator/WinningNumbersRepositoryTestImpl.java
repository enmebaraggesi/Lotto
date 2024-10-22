package com.lotto.domain.numbergenerator;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

class WinningNumbersRepositoryTestImpl implements WinningNumbersRepository {
    
    Map<String, WinningNumbers> inMemoryWinningNumbers = new HashMap<>();
    
    @Override
    public <S extends WinningNumbers> @NotNull S save(final @NotNull S entity) {
        inMemoryWinningNumbers.put(entity.id(), entity);
        return entity;
    }
    
    @Override
    public Optional<WinningNumbers> findByDate(final LocalDateTime drawDate) {
        return inMemoryWinningNumbers.values()
                                     .stream()
                                     .filter(number -> number.date().equals(drawDate))
                                     .findFirst();
    }
    
    @Override
    public boolean existsByDate(final LocalDateTime drawDate) {
        return inMemoryWinningNumbers.values()
                                     .stream()
                                     .anyMatch(number -> number.date().equals(drawDate));
    }
    
    @Override
    public <S extends WinningNumbers> S insert(final S entity) {
        return null;
    }
    
    @Override
    public <S extends WinningNumbers> List<S> insert(final Iterable<S> entities) {
        return List.of();
    }
    
    @Override
    public <S extends WinningNumbers> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }
    
    @Override
    public <S extends WinningNumbers> List<S> findAll(final Example<S> example) {
        return List.of();
    }
    
    @Override
    public <S extends WinningNumbers> List<S> findAll(final Example<S> example, final Sort sort) {
        return List.of();
    }
    
    @Override
    public <S extends WinningNumbers> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }
    
    @Override
    public <S extends WinningNumbers> long count(final Example<S> example) {
        return 0;
    }
    
    @Override
    public <S extends WinningNumbers> boolean exists(final Example<S> example) {
        return false;
    }
    
    @Override
    public <S extends WinningNumbers, R> R findBy(final Example<S> example, final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
    
    @Override
    public <S extends WinningNumbers> List<S> saveAll(final Iterable<S> entities) {
        return List.of();
    }
    
    @Override
    public Optional<WinningNumbers> findById(final String s) {
        return Optional.empty();
    }
    
    @Override
    public boolean existsById(final String s) {
        return false;
    }
    
    @Override
    public List<WinningNumbers> findAll() {
        return List.of();
    }
    
    @Override
    public List<WinningNumbers> findAllById(final Iterable<String> strings) {
        return List.of();
    }
    
    @Override
    public long count() {
        return 0;
    }
    
    @Override
    public void deleteById(final String s) {
    
    }
    
    @Override
    public void delete(final WinningNumbers entity) {
    
    }
    
    @Override
    public void deleteAllById(final Iterable<? extends String> strings) {
    
    }
    
    @Override
    public void deleteAll(final Iterable<? extends WinningNumbers> entities) {
    
    }
    
    @Override
    public void deleteAll() {
    
    }
    
    @Override
    public List<WinningNumbers> findAll(final Sort sort) {
        return List.of();
    }
    
    @Override
    public Page<WinningNumbers> findAll(final Pageable pageable) {
        return null;
    }
}
