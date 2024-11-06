package com.lotto.domain.resultannouncer;

import com.lotto.domain.numberreceiver.NumberReceiverFacade;
import com.lotto.domain.numberreceiver.dto.TicketDto;
import com.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import com.lotto.domain.resultchecker.ResultCheckerFacade;
import com.lotto.domain.resultchecker.dto.ResultDto;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static com.lotto.domain.resultannouncer.AnnouncementMessage.ALREADY_CHECKED;
import static com.lotto.domain.resultannouncer.AnnouncementMessage.LOSE_MESSAGE;
import static com.lotto.domain.resultannouncer.AnnouncementMessage.WAIT_MESSAGE;
import static com.lotto.domain.resultannouncer.AnnouncementMessage.WIN_MESSAGE;

@AllArgsConstructor
public class ResultAnnouncerFacade {
    
    private static final LocalTime RESULTS_ANNOUNCEMENT_TIME = LocalTime.of(12, 1);
    private final ResultCheckerFacade resultCheckerFacade;
    private final NumberReceiverFacade numberReceiverFacade;
    private final ResponseRepository responseRepository;
    private final Clock clock;
    
    public ResultAnnouncerResponseDto checkResult(final String id) {
        if (responseRepository.existsById(id)) {
            Optional<Response> cachedResponse = responseRepository.findById(id);
            if (cachedResponse.isPresent()) {
                return ResultMapper.mapToAnnouncementMessageDto(cachedResponse.get(), ALREADY_CHECKED.message);
            }
        }
        TicketDto ticketDto = numberReceiverFacade.findById(id);
        if (isBeforeDrawDate(ticketDto)) {
            return ResultMapper.mapToAnnouncementMessageDto(Response.builder().build(), WAIT_MESSAGE.message);
        }
        ResultDto resultDto = resultCheckerFacade.findById(id);
        Response response = responseRepository.save(ResultMapper.mapResultDtoToResponse(resultDto));
        if (response.isWinner()) {
            return ResultMapper.mapToAnnouncementMessageDto(response, WIN_MESSAGE.message);
        }
        return ResultMapper.mapToAnnouncementMessageDto(response, LOSE_MESSAGE.message);
    }
    
    private boolean isBeforeDrawDate(final TicketDto ticketDto) {
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime announcementDate = LocalDateTime.of(ticketDto.drawDate().toLocalDate(), RESULTS_ANNOUNCEMENT_TIME);
        return now.isBefore(announcementDate);
    }
}
