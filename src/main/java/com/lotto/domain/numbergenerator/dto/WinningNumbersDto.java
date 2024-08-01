package com.lotto.domain.numbergenerator.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record WinningNumbersDto(Set<Integer> winningNumbers, LocalDateTime date) {

}
