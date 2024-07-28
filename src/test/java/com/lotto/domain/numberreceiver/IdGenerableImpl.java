package com.lotto.domain.numberreceiver;

class IdGenerableImpl implements IdGenerable {
    
    private final String id;
    
    IdGenerableImpl() {
        this.id = "ABCD";
    }
    
    @Override
    public String generateId() {
        return id;
    }
}
