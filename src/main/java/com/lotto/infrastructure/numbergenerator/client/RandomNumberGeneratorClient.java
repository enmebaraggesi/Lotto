package com.lotto.infrastructure.numbergenerator.client;

import com.lotto.domain.numbergenerator.RandomNumberGenerable;
import com.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
public class RandomNumberGeneratorClient implements RandomNumberGenerable {
    
    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;
    
    @Override
    public SixRandomNumbersDto generateSixWinningNumbers(int lowerBand, int upperBand, int count) {
        log.info("Generating six winning numbers");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(headers);
        try {
            ResponseEntity<List<Integer>> response = makeGetRequest(lowerBand, upperBand, count, httpEntity);
            Set<Integer> sixDistinctNumbers = getSixRandomDistinctNumbers(response);
            if (sixDistinctNumbers.size() != 6) {
                log.error("Six winning numbers did not contain 6 digits");
                return generateSixWinningNumbers(lowerBand, upperBand, count);
            }
            return SixRandomNumbersDto.builder()
                                      .numbers(sixDistinctNumbers)
                                      .build();
        } catch (ResourceAccessException e) {
            log.error("Error while generating six winning numbers", e);
            return SixRandomNumbersDto.builder().build();
        }
    }
    
    private ResponseEntity<List<Integer>> makeGetRequest(final int lowerBand, final int upperBand, final int count, final HttpEntity<HttpHeaders> httpEntity) {
        String urlForService = getUrlForService("/api/v1.0/random");
        String url = UriComponentsBuilder.fromHttpUrl(urlForService)
                                         .queryParam("min", lowerBand)
                                         .queryParam("max", upperBand)
                                         .queryParam("count", count)
                                         .toUriString();
        return restTemplate.exchange(url,
                                     HttpMethod.GET,
                                     httpEntity,
                                     new ParameterizedTypeReference<>() {
                                     });
    }
    
    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
    
    private Set<Integer> getSixRandomDistinctNumbers(final ResponseEntity<List<Integer>> response) {
        List<Integer> numbers = response.getBody();
        if (numbers == null) {
            log.error("Response body did not contain any numbers");
            return Collections.emptySet();
        }
        log.info("Response body contained {}", numbers);
        Set<Integer> distinctNumbers = new HashSet<>(numbers);
        return distinctNumbers.stream()
                              .limit(6)
                              .collect(Collectors.toSet());
    }
}
