package com.lotto.apivalidationerror;

import com.lotto.BaseIntegrationTest;
import com.lotto.infrastructure.apivalidation.ApiValidationErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApiValidationErrorIntegrationTest extends BaseIntegrationTest {
    
    @Test
    public void should_return_error_message_when_input_numbers_are_empty() throws Exception {
        //given & when
        ResultActions perform = mockMvc.perform(post("/inputNumbers")
                                                        .content("""
                                                                 {
                                                                 "inputNumbers": []
                                                                 }
                                                                 """.trim())
                                                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto responseDto = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(responseDto.errors()).contains("inputNumbers must not be empty");
    }
    
    @Test
    public void should_return_error_messages_when_input_numbers_is_null() throws Exception {
        //given & when
        ResultActions perform = mockMvc.perform(post("/inputNumbers")
                                                        .content("""
                                                                 {}
                                                                 """.trim())
                                                        .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto responseDto = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(responseDto.errors()).containsExactlyInAnyOrder("inputNumbers must not be empty",
                                                                   "inputNumbers must not be null"
        );
    }
}
