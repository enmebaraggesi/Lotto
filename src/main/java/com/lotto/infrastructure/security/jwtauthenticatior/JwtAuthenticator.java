package com.lotto.infrastructure.security.jwtauthenticatior;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lotto.infrastructure.security.token.dto.JwtResponseDto;
import com.lotto.infrastructure.security.token.dto.TokenRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@AllArgsConstructor
public class JwtAuthenticator {
    
    private final AuthenticationManager authenticationManager;
    private final JwtConfigurationProperties properties;
    private final Clock clock = Clock.system(ZoneOffset.UTC);
    
    public JwtResponseDto authenticateAndGenerateToken(final TokenRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.username(), requestDto.password())
        );
        User user = (User) authentication.getPrincipal();
        String token = createToken(user);
        String username = user.getUsername();
        return JwtResponseDto.builder()
                             .username(username)
                             .token(token)
                             .build();
    }
    
    private String createToken(final User user) {
        String subject = user.getUsername();
        Instant issuedAt = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
        Instant expiresAt = issuedAt.plus(Duration.ofDays(properties.expirationDs()));
        String issuer = properties.issuer();
        String secret = properties.secret();
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                  .withSubject(subject)
                  .withIssuedAt(issuedAt)
                  .withExpiresAt(expiresAt)
                  .withIssuer(issuer)
                  .sign(algorithm);
    }
}
