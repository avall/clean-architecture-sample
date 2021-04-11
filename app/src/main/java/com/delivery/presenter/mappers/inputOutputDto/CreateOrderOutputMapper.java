package com.delivery.presenter.mappers.inputOutputDto;

import com.delivery.core.domain.Order;
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
public interface CreateOrderOutputMapper {

    @Mapping(target = "success",           expression = "java(true)")
    @Mapping(target = "message",           constant = "order created successfully")
    ApiResponse map(Order order);
}
