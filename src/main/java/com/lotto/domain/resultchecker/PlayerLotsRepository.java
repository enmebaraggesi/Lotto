package com.lotto.domain.resultchecker;

import java.util.Optional;

interface PlayerLotsRepository {
    
    PlayerLot save(PlayerLot playerLot);
    
    Optional<PlayerLot> findById(String id);
}
