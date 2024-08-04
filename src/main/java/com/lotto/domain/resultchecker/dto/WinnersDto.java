package com.lotto.domain.resultchecker.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record WinnersDto(List<ResultDto> results, String message) {

}
