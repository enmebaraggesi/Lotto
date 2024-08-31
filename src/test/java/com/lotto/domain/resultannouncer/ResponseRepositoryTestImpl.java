package com.lotto.domain.resultannouncer;

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

class ResponseRepositoryTestImpl implements ResponseRepository {
    
    private final Map<String, Response> inMemoryDatabase = new ConcurrentHashMap<>();
    
    @Override
    public boolean existsById(final String id) {
        return inMemoryDatabase.containsKey(id);
    }
    
    @Override
    public Optional<Response> findById(final String id) {
        return Optional.of(inMemoryDatabase.get(id));
    }
    
    @Override
    public Response save(final Response response) {
        inMemoryDatabase.put(response.id(), response);
        return response;
    }
    
    @Override
    public <S extends Response> S insert(final S entity) {
        return null;
    }
    
    @Override
    public <S extends Response> List<S> insert(final Iterable<S> entities) {
        return List.of();
    }
    
    @Override
    public <S extends Response> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }
    
    @Override
    public <S extends Response> List<S> findAll(final Example<S> example) {
        return List.of();
    }
    
    @Override
    public <S extends Response> List<S> findAll(final Example<S> example, final Sort sort) {
        return List.of();
    }
    
    @Override
    public <S extends Response> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }
    
    @Override
    public <S extends Response> long count(final Example<S> example) {
        return 0;
    }
    
    @Override
    public <S extends Response> boolean exists(final Example<S> example) {
        return false;
    }
    
    @Override
    public <S extends Response, R> R findBy(final Example<S> example, final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
    
    @Override
    public <S extends Response> List<S> saveAll(final Iterable<S> entities) {
        return List.of();
    }
    
    @Override
    public List<Response> findAll() {
        return List.of();
    }
    
    @Override
    public List<Response> findAllById(final Iterable<String> strings) {
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
    public void delete(final Response entity) {
    
    }
    
    @Override
    public void deleteAllById(final Iterable<? extends String> strings) {
    
    }
    
    @Override
    public void deleteAll(final Iterable<? extends Response> entities) {
    
    }
    
    @Override
    public void deleteAll() {
    
    }
    
    @Override
    public List<Response> findAll(final Sort sort) {
        return List.of();
    }
    
    @Override
    public Page<Response> findAll(final Pageable pageable) {
        return null;
    }
}
