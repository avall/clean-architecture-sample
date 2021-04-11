package com.delivery.presenter.rest.api.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import com.delivery.core.usecases.customer.CreateCustomerUseCase;
import com.delivery.dto.SignUpRequest;
import com.delivery.presenter.mappers.inputOutputDto.CreateCustomerInputMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class InputValuesMapperTest {

    @InjectMocks
    private CreateCustomerInputMapper inputMapper = Mappers.getMapper(CreateCustomerInputMapper.class);

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void mapReturnsCreateCustomerInput() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("name", "email@email.com", "address", "password");
        CreateCustomerUseCase.InputValues inputValues = CreateCustomerUseCase.InputValues.builder()
            .name(signUpRequest.getName())
            .email(signUpRequest.getEmail())
            .address(signUpRequest.getAddress())
            .password(passwordEncoder.encode(signUpRequest.getPassword()))
            .build();;

        // and
        doReturn("encrypt")
                .when(passwordEncoder)
                .encode(eq("password"));

        // when
        CreateCustomerUseCase.InputValues actual = inputMapper.map(signUpRequest);

        // then
        assertThat(actual).isEqualToIgnoringGivenFields(inputValues, "password");
        assertThat(actual.getPassword()).isEqualTo("encrypt");
    }
}