package com.lotto.infrastructure.security.jwtauthenticatior;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Collections.emptyList;

@Component
@AllArgsConstructor
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {
    
    private final JwtConfigurationProperties properties;
    
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken token = getUsernamePasswordAuthenticationToken(authorization);
            SecurityContextHolder.getContext().setAuthentication(token);
            filterChain.doFilter(request, response);
        }
        filterChain.doFilter(request, response);
    }
    
    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(final String authorization) {
        String secretKey = properties.secret();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(authorization.substring(7));
        return new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, emptyList());
    }
}
