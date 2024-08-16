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
    
    @Override
    public SixRandomNumbersDto generateSixWinningNumbers() {
        String url = UriComponentsBuilder.fromHttpUrl(getUrlForService("/api/v1.0/random"))
                                         .queryParam("min", 1)
                                         .queryParam("max", 99)
                                         .queryParam("count", 6)
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
        return "http://ec2-3-120-147-150.eu-central-1.compute.amazonaws.com" + ":" + "9090" + service;
    }
}
