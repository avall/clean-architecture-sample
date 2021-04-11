package com.delivery.presenter.mappers.domainDto;

import com.delivery.core.domain.OrderItem;
import com.delivery.database.entities.IdConverter;
import com.delivery.dto.OrderItemResponse;
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
    }
)
public interface OrderItemDomainDtoMapper extends BaseDomainDtoMapper<OrderItem, OrderItemResponse> {
  @Mapping(source = "product.name",       target = "name")
  @Mapping(source = "product.price",      target = "price")
  @Mapping(source = "quantity",           target = "quantity")
  @Mapping(source = "total",              target = "total")
  OrderItemResponse mapToDto(OrderItem customer);
  List<OrderItemResponse> mapToDto(List<OrderItem> customer);

  OrderItem mapToDomain(OrderItemResponse customer);
  List<OrderItem> mapToDomain(List<OrderItemResponse> customer);
}
