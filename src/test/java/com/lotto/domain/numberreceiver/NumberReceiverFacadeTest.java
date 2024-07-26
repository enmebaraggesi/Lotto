package com.lotto.domain.numberreceiver;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest {
    
    @Test
    public void should_return_success_when_user_give_six_numbers() {
        //given
        NumberReceiverFacade facade = new NumberReceiverFacade();
        //when
        String result = facade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6));
        //then
        assertThat(result).isEqualTo("success");
    }
    
    @Test
    public void should_return_fail_when_user_give_less_than_six_numbers() {
        //given
        NumberReceiverFacade facade = new NumberReceiverFacade();
        //when
        String result = facade.inputNumbers(Set.of(1, 2, 3, 4, 5));
        //then
        assertThat(result).isEqualTo("failure");
    }
    
    @Test
    public void should_return_fail_when_user_give_more_than_six_numbers() {
        //given
        NumberReceiverFacade facade = new NumberReceiverFacade();
        //when
        String result = facade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6,7));
        //then
        assertThat(result).isEqualTo("failure");
    }
    
    @Test
    public void should_return_fail_when_user_give_at_least_one_number_out_of_range() {
        //given
        NumberReceiverFacade facade = new NumberReceiverFacade();
        //when
        String result = facade.inputNumbers(Set.of(1, 200, 3, 4, 5, 6));
        //then
        assertThat(result).isEqualTo("failure");
    }
}