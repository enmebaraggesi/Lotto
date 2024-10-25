package com.lotto.infrastructure.security.token;

import com.lotto.infrastructure.security.jwtauthenticatior.JwtAuthenticator;
import com.lotto.infrastructure.security.token.dto.JwtResponseDto;
import com.lotto.infrastructure.security.token.dto.TokenRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("token")
@AllArgsConstructor
public class TokenRestController {
    
    private final JwtAuthenticator jwtAuthenticator;
    
    @PostMapping
    public ResponseEntity<JwtResponseDto> authenticateAndGenerateToken(@RequestBody @Valid TokenRequestDto requestDto) {
        JwtResponseDto responseDto = jwtAuthenticator.authenticateAndGenerateToken(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
