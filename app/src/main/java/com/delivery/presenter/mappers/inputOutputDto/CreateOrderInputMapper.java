package com.delivery.presenter.mappers.inputOutputDto;

import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.order.CreateOrderUseCase;
import com.delivery.dto.OrderRequest;
import com.delivery.dto.OrderRequestItem;
import com.delivery.presenter.usecases.security.UserPrincipal;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    componentModel = "spring",
    imports = {
        Identity.class
    },
    uses = {
    }
)
public interface CreateOrderInputMapper {

    @Mapping(target = "customer.id",          expression    = "java(new Identity(userPrincipal.getId()))")
    @Mapping(target = "customer.name",        source        = "userPrincipal.name")
    @Mapping(target = "customer.email",       source        = "userPrincipal.email")
    @Mapping(target = "customer.address",     source        = "userPrincipal.address")
    @Mapping(target = "customer.password",    source        = "userPrincipal.password")
    @Mapping(target = "storeId",              expression    = "java(new Identity(orderRequest.getStoreId()))")
    @Mapping(target = "orderItems",           source        = "orderRequest.orderItems")
    CreateOrderUseCase.InputValues map(OrderRequest orderRequest, UserPrincipal userPrincipal);


    @Mapping(target = "id",                   expression    = "java(new Identity(orderRequestItem.getId()))")
    @Mapping(target = "quantity",             source        = "quantity")
    CreateOrderUseCase.InputItem map(OrderRequestItem orderRequestItem);

    List<CreateOrderUseCase.InputItem> map(List<OrderRequestItem> orderRequestItem);

}
