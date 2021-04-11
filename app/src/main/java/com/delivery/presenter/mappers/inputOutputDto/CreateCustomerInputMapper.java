package com.delivery.presenter.mappers.inputOutputDto;

import com.delivery.core.usecases.customer.CreateCustomerUseCase;
import com.delivery.dto.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    componentModel = "spring",
    imports = {
    },
    uses = {
    }
)
public abstract class CreateCustomerInputMapper {
    @Autowired private PasswordEncoder passwordEncoder;

    @Mapping(target = "password",           expression    = "java(getPasswordEncoder().encode(signUpRequest.getPassword()))")
    @Mapping(target = "name",               source        = "signUpRequest.name")
    @Mapping(target = "email",              source        = "signUpRequest.email")
    @Mapping(target = "address",            source        = "signUpRequest.address")
    public abstract CreateCustomerUseCase.InputValues map(SignUpRequest signUpRequest);

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
