package com.lotto.domain.numberreceiver;

import java.time.Clock;

public class NumberRecieverConfig {
    
    NumberReceiverFacade forTests(final TicketRepository repository,
                                  final Clock clock,
                                  final IdGenerable idGenerator) {
        NumberValidator validator = new NumberValidator();
        return new NumberReceiverFacade(validator, repository, clock, idGenerator);
    }
}
