package com.lotto.domain.resultannouncer;

import com.lotto.domain.resultannouncer.dto.ResponseDto;
import com.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import com.lotto.domain.resultchecker.dto.ResultDto;

class ResultMapper {
    
    static Response mapResultDtoToResponse(final ResultDto resultDto) {
        return Response.builder()
                       .id(resultDto.id())
                       .numbers(resultDto.numbers())
                       .hitNumbers(resultDto.hitNumbers())
                       .drawDate(resultDto.drawDate())
                       .isWinner(resultDto.isWinner())
                       .build();
    }
    
    static ResultAnnouncerResponseDto mapToAnnouncementMessageDto(final Response response, final String message) {
        ResponseDto responseDto = ResponseDto.builder()
                                       .id(response.id())
                                       .numbers(response.numbers())
                                       .hitNumbers(response.hitNumbers())
                                       .drawDate(response.drawDate())
                                       .isWinner(response.isWinner())
                                       .build();
        return new ResultAnnouncerResponseDto(responseDto, message);
    }
}
