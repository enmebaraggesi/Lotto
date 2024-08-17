package com.lotto.infrastructure.numbergenerator.client;

import com.lotto.domain.numbergenerator.RandomNumberGenerable;
import com.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
public class RandomNumberGeneratorClient implements RandomNumberGenerable {
    
    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;
    
    @Override
    public SixRandomNumbersDto generateSixWinningNumbers(int lowerBand, int upperBand, int count) {
        String urlForService = getUrlForService("/api/v1.0/random");
        String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                                         .queryParam("min", lowerBand)
                                         .queryParam("max", upperBand)
                                         .queryParam("count", count)
                                         .toUriString();
        ResponseEntity<List<Integer>> response = restTemplate.exchange(url,
                                                                       HttpMethod.GET,
                                                                       null,
                                                                       new ParameterizedTypeReference<>() {
                                                                       });
        return SixRandomNumbersDto.builder()
                                  .numbers(new HashSet<>(response.getBody()))
                                  .build();
    }
    
    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}
