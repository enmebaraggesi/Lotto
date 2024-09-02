package com.lotto.domain.resultchecker;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface PlayerLotsRepository extends MongoRepository<PlayerLot, String> {
    
    @Override
    Optional<PlayerLot> findById(String id);
}
