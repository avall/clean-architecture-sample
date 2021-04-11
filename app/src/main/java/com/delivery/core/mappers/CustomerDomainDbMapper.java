package com.delivery.core.mappers;

import com.delivery.core.domain.Customer;
import com.delivery.database.entities.CustomerDb;
import com.delivery.database.entities.IdConverter;
import java.util.Arrays;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
    componentModel = "spring",
    imports = {
        Arrays.class,
        IdConverter.class,
        com.delivery.core.domain.Identity.class
    },
    uses = {
    }
)
public interface CustomerDomainDbMapper extends BaseDomainDbMapper<Customer, CustomerDb> {
  @Mapping(expression = "java(IdConverter.convertId(customer.getId()))",     target = "id")
  @Mapping(source = "name",     target = "name")
  @Mapping(source = "email",    target = "email")
  @Mapping(source = "address",  target = "address")
  @Mapping(source = "password", target = "password")
  CustomerDb mapToDb(Customer customer);
  List<CustomerDb> mapToDb(List<Customer> customer);

  @Mapping(expression = "java(new Identity(customer.getId()))",     target = "id")
  @Mapping(source = "name",     target = "name")
  @Mapping(source = "email",    target = "email")
  @Mapping(source = "address",  target = "address")
  @Mapping(source = "password", target = "password")
  Customer mapToDomain(CustomerDb customer);
  List<Customer> mapToDomain(List<CustomerDb> customer);
}
