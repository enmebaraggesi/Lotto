package com.lotto.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.Set;

public record WinningNumbers(String id, Set<Integer> winningNumbers, LocalDateTime date) {

}
