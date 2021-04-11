package com.delivery.presenter.usecases.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.dto.SignInRequest;
import com.delivery.presenter.mappers.inputOutputDto.AuthenticateCustomerUseCaseInputMapper;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthenticateCustomerUseCaseInputMapperTest {

    @Test
    public void mapReturnsUsernameAuthenticationToken() {
        // given
        final String email = "email";
        final String password = "password";
        SignInRequest signInRequest = SignInRequest.builder()
        .email(email).password(password).build();

        // when
        UsernamePasswordAuthenticationToken actual = Mappers.getMapper(
            AuthenticateCustomerUseCaseInputMapper.class)
                .map(signInRequest).getAuthenticationToken();

        // then
        assertThat(actual.getPrincipal()).isEqualTo(email);
        assertThat(actual.getCredentials()).isEqualTo(password);
    }
}