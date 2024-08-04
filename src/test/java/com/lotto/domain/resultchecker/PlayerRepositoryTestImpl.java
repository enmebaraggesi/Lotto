package com.lotto.domain.resultchecker;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class PlayerRepositoryTestImpl implements PlayerLotsRepository {
    
    private final Map<String, PlayerLot> inMemoryDatabase = new ConcurrentHashMap<>();
    
    @Override
    public PlayerLot save(final PlayerLot playerLot) {
        inMemoryDatabase.put(playerLot.id(), playerLot);
        return playerLot;
    }
    
    @Override
    public Optional<PlayerLot> findById(final String id) {
        return Optional.of(inMemoryDatabase.get(id));
    }
}
