package com.lotto.infrastructure.security.token.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(String username, String token) {

}
