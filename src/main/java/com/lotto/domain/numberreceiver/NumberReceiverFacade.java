package com.lotto.domain.numberreceiver;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class NumberReceiverFacade {
    
    private final NumberValidator validator;
    
    public InputNumbersResultDto inputNumbers(Set<Integer> userNumbers) {
        if (validator.filterAllNumbersInRange(userNumbers)) {
            return InputNumbersResultDto.builder()
                                        .message("success")
                                        .build();
        }
        return InputNumbersResultDto.builder()
                                    .message("failure")
                                    .build();
    }
}
