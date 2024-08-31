package com.lotto.domain.resultchecker;

import org.springframework.data.mongodb.repository.MongoRepository;

interface PlayerLotsRepository extends MongoRepository<PlayerLot, String> {

}
