package com.lotto.infrastructure.security.jwtauthenticatior;

import com.lotto.domain.resultchecker.ResultCheckerFacade;
import com.lotto.domain.resultchecker.dto.ResultDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@AllArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {
    
    private final ResultCheckerFacade resultCheckerFacade;
    
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        ResultDto resultDto = resultCheckerFacade.findById(username);
        return getUser(resultDto);
    }
    
    private UserDetails getUser(final ResultDto resultDto) {
        String password = String.valueOf(resultDto.numbers());
        return new User(resultDto.id(), password, Collections.emptyList());
    }
}
