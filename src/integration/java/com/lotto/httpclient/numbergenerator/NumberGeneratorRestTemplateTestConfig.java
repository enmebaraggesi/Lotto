package com.lotto.httpclient.numbergenerator;

import com.lotto.domain.numbergenerator.RandomNumberGenerable;
import com.lotto.infrastructure.numbergenerator.client.RandomNumberGeneratorClientConfig;
import com.lotto.infrastructure.numbergenerator.client.RandomNumberGeneratorClientProperties;
import org.springframework.web.client.RestTemplate;

public class NumberGeneratorRestTemplateTestConfig extends RandomNumberGeneratorClientConfig {
    
    public RandomNumberGenerable remoteTestNumberGeneratorClient(int port, int connectionTimeout, int readTimeout) {
        RandomNumberGeneratorClientProperties properties = new RandomNumberGeneratorClientProperties("http://localhost",
                                                                                                     "/api/v1.0/random",
                                                                                                     port,
                                                                                                     connectionTimeout,
                                                                                                     readTimeout);
        RestTemplate restTemplate = restTemplate(restTemplateResponseErrorHandler(),
                                                 properties);
        return randomNumberGeneratorClient(restTemplate, properties);
    }
}
