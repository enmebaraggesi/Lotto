package com.lotto.domain.numberreceiver.dto;

import lombok.Builder;

@Builder
public record InputNumbersResultDto(TicketDto ticket, String message) {

}
