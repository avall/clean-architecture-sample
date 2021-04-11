package com.delivery.presenter.mappers.inputOutputDto;

import com.delivery.dto.SignInRequest;
import com.delivery.presenter.usecases.security.AuthenticateCustomerUseCase.InputValues;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Mapper(
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    componentModel = "spring",
    imports = {
        UsernamePasswordAuthenticationToken.class
    },
    uses = {
    }
)
public interface AuthenticateCustomerUseCaseInputMapper {
    @Mapping(target = "authenticationToken",           expression    = "java(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()))")
    InputValues map(SignInRequest signInRequest);
}
