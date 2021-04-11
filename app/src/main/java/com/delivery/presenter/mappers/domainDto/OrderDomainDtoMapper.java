package com.delivery.presenter.mappers.domainDto;

import com.delivery.core.domain.Order;
import com.delivery.database.entities.IdConverter;
import com.delivery.dto.OrderResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    componentModel = "spring",
    imports = {
        IdConverter.class,
        com.delivery.core.domain.Identity.class
    },
    uses = {
        OrderItemDomainDtoMapper.class,
        CustomerDomainDtoMapper.class,
        StoreDomainDtoMapper.class,
    }
)
public interface OrderDomainDtoMapper extends BaseDomainDtoMapper<Order, OrderResponse> {
  @Mapping(expression = "java(order.getId().getNumber())",      target = "id")
  @Mapping(source = "createdAt",                                target = "date")
  @Mapping(source = "updatedAt",                                target = "lastUpdate")
  @Mapping(source = "total",                                    target = "total")
  @Mapping(source = "status",                                   target = "status")
  @Mapping(source = "customer.name",                            target = "contact")
  @Mapping(source = "orderItems",                               target = "orderItems")
  @Mapping(source = "customer",                                 target = "customer")
  @Mapping(source = "store",                                    target = "store")
  OrderResponse mapToDto(Order order);
  List<OrderResponse> mapToDto(List<Order> order);

  @Mapping(expression = "java(new Identity(order.getId()))",     target = "id")
  Order mapToDomain(OrderResponse order);
  List<Order> mapToDomain(List<OrderResponse> order);
}
