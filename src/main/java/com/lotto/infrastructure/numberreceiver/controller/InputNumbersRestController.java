package com.lotto.infrastructure.numberreceiver.controller;

import com.lotto.domain.numberreceiver.NumberReceiverFacade;
import com.lotto.domain.numberreceiver.dto.InputNumbersResultDto;
import com.lotto.infrastructure.numberreceiver.controller.dto.InputNumbersRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@Log4j2
@AllArgsConstructor
class InputNumbersRestController {
    
    private final NumberReceiverFacade numberReceiverFacade;
    
    @PostMapping("/inputNumbers")
    public ResponseEntity<InputNumbersResultDto> inputNumbers(@RequestBody @Valid InputNumbersRequestDto requestDto) {
        Set<Integer> inputNumbers = new HashSet<>(requestDto.inputNumbers());
        InputNumbersResultDto resultDto = numberReceiverFacade.inputNumbers(inputNumbers);
        return ResponseEntity.ok(resultDto);
    }
}
