package com.lotto.domain.resultannouncer;

import com.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import com.lotto.domain.resultchecker.ResultCheckerFacade;
import com.lotto.domain.resultchecker.dto.ResultDto;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.lotto.domain.resultannouncer.AnnouncementMessage.ALREADY_CHECKED;
import static com.lotto.domain.resultannouncer.AnnouncementMessage.ID_DOES_NOT_EXIST_MESSAGE;
import static com.lotto.domain.resultannouncer.AnnouncementMessage.LOSE_MESSAGE;
import static com.lotto.domain.resultannouncer.AnnouncementMessage.WAIT_MESSAGE;
import static com.lotto.domain.resultannouncer.AnnouncementMessage.WIN_MESSAGE;

@AllArgsConstructor
class ResultAnnouncerFacade {
    
    private final ResultCheckerFacade resultCheckerFacade;
    private final ResponseRepository responseRepository;
    private final Clock clock;
    
    ResultAnnouncerResponseDto checkResult(final String id) {
        if (responseRepository.existsById(id)) {
            Optional<Response> cachedResponse = responseRepository.findById(id);
            if (cachedResponse.isPresent()) {
                return ResultMapper.mapToAnnouncementMessageDto(cachedResponse.get(), ALREADY_CHECKED.message);
            }
        }
        ResultDto resultDto = resultCheckerFacade.findById(id);
        if (resultDto == null) {
            return ResultAnnouncerResponseDto.builder()
                                             .message(ID_DOES_NOT_EXIST_MESSAGE.message)
                                             .build();
        }
        Response response = responseRepository.save(ResultMapper.mapResultDtoToResponse(resultDto));
        if (LocalDateTime.now(clock).isBefore(response.drawDate())) {
            return ResultMapper.mapToAnnouncementMessageDto(response, WAIT_MESSAGE.message);
        }
        if (response.isWinner()) {
            return ResultMapper.mapToAnnouncementMessageDto(response, WIN_MESSAGE.message);
        }
        return ResultMapper.mapToAnnouncementMessageDto(response, LOSE_MESSAGE.message);
    }
}
