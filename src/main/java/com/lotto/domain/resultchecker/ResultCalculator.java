package com.lotto.domain.resultchecker;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class ResultCalculator {
    
    private final static int NUMBERS_WHEN_PLAYER_WON = 3;
    
    List<PlayerLot> retrieveWinners(List<Ticket> tickets, Set<Integer> winningNumbers) {
        return tickets.stream()
                               .map(ticket -> {
                                   Set<Integer> hitNumbers = calculateHits(winningNumbers, ticket);
                                   return buildPlayerLot(ticket, hitNumbers);
                               })
                               .toList();
    }
    
    private Set<Integer> calculateHits(Set<Integer> winningNumbers, Ticket ticket) {
        return ticket.numbers().stream()
                     .filter(winningNumbers::contains)
                     .collect(Collectors.toSet());
    }
    
    private PlayerLot buildPlayerLot(Ticket ticket, Set<Integer> hitNumbers) {
        PlayerLot.PlayerLotBuilder builder = PlayerLot.builder()
                                                      .id(ticket.id())
                                                      .numbers(ticket.numbers())
                                                      .drawDate(ticket.drawDate());
        if (isWinner(hitNumbers)) {
            builder.isWinner(true);
        }
        return builder.hitNumbers(hitNumbers)
                      .build();
    }
    
    private boolean isWinner(Set<Integer> hitNumbers) {
        return hitNumbers.size() >= NUMBERS_WHEN_PLAYER_WON;
    }
}
