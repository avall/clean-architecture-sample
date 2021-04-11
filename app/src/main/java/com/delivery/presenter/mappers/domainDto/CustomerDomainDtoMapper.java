package com.delivery.presenter.mappers.domainDto;

import com.delivery.core.domain.Customer;
import com.delivery.database.entities.IdConverter;
import com.delivery.dto.CustomerResponse;
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
public interface CustomerDomainDtoMapper extends BaseDomainDtoMapper<Customer, CustomerResponse> {
  @Mapping(expression = "java(customer.getId().getNumber())",      target = "id")
  @Mapping(source = "name",       target = "name")
  @Mapping(source = "email",      target = "email")
  @Mapping(source = "address",    target = "address")
  CustomerResponse mapToDto(Customer customer);
  List<CustomerResponse> mapToDto(List<Customer> customer);

  @Mapping(expression = "java(new Identity(customer.getId()))",     target = "id")
  @Mapping(source = "name",       target = "name")
  @Mapping(source = "email",      target = "email")
  Customer mapToDomain(CustomerResponse customer);
  List<Customer> mapToDomain(List<CustomerResponse> customer);
}
