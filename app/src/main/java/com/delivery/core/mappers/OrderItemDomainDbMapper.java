package com.delivery.core.mappers;

import com.delivery.core.domain.OrderItem;
import com.delivery.database.entities.IdConverter;
import com.delivery.database.entities.OrderItemDb;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    componentModel = "spring",
    imports = {
        Arrays.class,
        HashSet.class,
        IdConverter.class,
        com.delivery.core.domain.Identity.class
    },
    uses = {
        ProductDomainDbMapper.class
    }
)
public interface OrderItemDomainDbMapper extends BaseDomainDbMapper<OrderItem, OrderItemDb> {
  @Mapping(expression = "java(IdConverter.convertId(orderItem.getId()))",     target = "id")
  @Mapping(source = "quantity",     target = "quantity")
  @Mapping(source = "product",      target = "product")
  @Mapping(source = "product.price",        target = "price")
  @Mapping(source = "total",        target = "total")
  OrderItemDb mapToDb(OrderItem orderItem);
  List<OrderItemDb> mapToDb(List<OrderItem> orderItem);

  @Mapping(expression = "java(new Identity(orderItem.getId()))",     target = "id")
  @Mapping(source = "quantity",     target = "quantity")
  @Mapping(source = "product",      target = "product")
  @Mapping(source = "total",        target = "total")
  OrderItem mapToDomain(OrderItemDb orderItem);
  List<OrderItem> mapToDomain(List<OrderItemDb> orderItem);
}
