package com.delivery.core.mappers;

import com.delivery.core.domain.Order;
import com.delivery.database.entities.IdConverter;
import com.delivery.database.entities.OrderDb;
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
        OrderItemDomainDbMapper.class,
        CustomerDomainDbMapper.class,
        StoreDomainDbMapper.class
    }
)
public interface OrderDomainDbMapper extends BaseDomainDbMapper<Order, OrderDb> {
  @Mapping(expression = "java(IdConverter.convertId(order.getId()))",     target = "id")
  @Mapping(source = "customer",     target = "customer")
  @Mapping(source = "store",      target = "store")
  @Mapping(source = "total",        target = "total")
  @Mapping(source = "status",        target = "status")
  @Mapping(source = "createdAt",   target = "createdAt")
  @Mapping(source = "updatedAt",   target = "updatedAt")
  OrderDb mapToDb(Order order);
  List<OrderDb> mapToDb(List<Order> order);

  @Mapping(expression = "java(new Identity(order.getId()))",     target = "id")
  @Mapping(source = "customer",     target = "customer")
  @Mapping(source = "store",      target = "store")
  @Mapping(source = "total",        target = "total")
  @Mapping(source = "status",        target = "status")
  @Mapping(source = "createdAt",   target = "createdAt")
  @Mapping(source = "updatedAt",   target = "updatedAt")
  Order mapToDomain(OrderDb order);
  List<Order> mapToDomain(List<OrderDb> order);
}
