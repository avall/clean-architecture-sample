package com.delivery.presenter.mappers.inputOutputDto;

import com.delivery.dto.AuthenticationResponse;
import com.delivery.presenter.usecases.security.AuthenticateCustomerUseCase.OutputValues;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.ResponseEntity;

@Mapper(
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    componentModel = "spring",
    imports = {
        ResponseEntity.class
    },
    uses = {
    }
)
public interface AuthenticateCustomerUseCaseOutputMapper {
    @Mapping(target = "token",           source    = "jwtToken")
    AuthenticationResponse map(OutputValues outputValues);
}
