package com.lotto;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

class AdjustableClock extends Clock {
    
    private Clock fixedClock;
    
    public AdjustableClock(Instant fixedInstant, ZoneId zone) {
        fixedClock = Clock.fixed(fixedInstant, zone);
    }
    
    public AdjustableClock(Instant fixedInstant) {
        this(fixedInstant, ZoneOffset.UTC);
    }
    
    private AdjustableClock(Clock innerClock) {
        fixedClock = innerClock;
    }
    
    @Override
    public ZoneId getZone() {
        return fixedClock.getZone();
    }
    
    @Override
    public Clock withZone(ZoneId zone) {
        return new AdjustableClock(fixedClock.withZone(zone));
    }
    
    @Override
    public Instant instant() {
        return fixedClock.instant();
    }
    
    public void advance(Duration offset) {
        fixedClock = Clock.offset(fixedClock, offset);
    }
}
