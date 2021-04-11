package com.delivery.presenter.usecases.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.dto.AuthenticationResponse;
import com.delivery.presenter.mappers.inputOutputDto.AuthenticateCustomerUseCaseOutputMapper;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AuthenticateCustomerUseCaseOutputMapperTest {

    @Test
    public void mapReturnsResponseOkWithJwtToken() {
        // given
        String jwtToken = "jwtToken";
        AuthenticateCustomerUseCase.OutputValues output = AuthenticateCustomerUseCase
            .OutputValues.builder().jwtToken(jwtToken).build();

        // when
        ResponseEntity<AuthenticationResponse> response =
            ResponseEntity
                .ok(Mappers.getMapper(AuthenticateCustomerUseCaseOutputMapper.class).map(output));

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getToken()).isEqualTo(jwtToken);
        assertThat(response.getBody().isSuccess()).isEqualTo(true);
    }
}