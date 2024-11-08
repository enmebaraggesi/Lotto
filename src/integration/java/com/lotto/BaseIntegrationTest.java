package com.lotto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(classes = {LottoSpringBootApplication.class, IntegrationConfiguration.class})
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@Testcontainers
public class BaseIntegrationTest {
    
    public static final String WIREMOCK_HOST = "http://localhost";
    
    @Autowired
    public MockMvc mockMvc;
    
    @Autowired
    public ObjectMapper objectMapper;
    
    @Autowired
    public AdjustableClock clock;
    
    @Container
    public static final MongoDBContainer MONGO_DB_CONTAINER = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    
    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
                                                                      .options(wireMockConfig().dynamicPort())
                                                                      .build();
    
    @DynamicPropertySource
    public static void updateProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", MONGO_DB_CONTAINER::getReplicaSetUrl);
        registry.add("lotto.number-generator.http.client.uri", () -> WIREMOCK_HOST);
        registry.add("lotto.number-generator.http.client.port", () -> wireMockServer.getPort());
        registry.add("lotto.number-generator.scheduler.lottery-run-occurrence", () -> "*/5 * * * * *");
        registry.add("lotto.result-checker.scheduler.lottery-run-occurrence", () -> "*/7 * * * * *");
    }
}
