package com.delivery.presenter.mappers.inputOutputDto;

import com.delivery.core.domain.Customer;
import com.delivery.dto.ApiResponse;
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
public interface CreateCustomerUseCaseOutputMapper {

    @Mapping(target = "success",           expression = "java(true)")
    @Mapping(target = "message",           constant = "registered successfully")
    ApiResponse map(Customer customer);
}
