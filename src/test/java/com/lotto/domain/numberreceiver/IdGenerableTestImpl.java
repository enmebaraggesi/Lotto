package com.lotto.domain.numberreceiver;

class IdGenerableTestImpl implements IdGenerable {
    
    private final String id;
    
    IdGenerableTestImpl() {
        this.id = "ABCD";
    }
    
    @Override
    public String generateId() {
        return id;
    }
}
