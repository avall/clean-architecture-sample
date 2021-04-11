package com.delivery.presenter.mappers.domainDto;

import com.delivery.core.domain.Product;
import com.delivery.database.entities.IdConverter;
import com.delivery.dto.ProductResponse;
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
public interface ProductDomainDtoMapper extends BaseDomainDtoMapper<Product, ProductResponse> {
  @Mapping(expression = "java(IdConverter.convertId(product.getId()))",               target = "id")
  @Mapping(source = "name",                                                           target = "name")
  @Mapping(source = "description",                                                    target = "description")
  @Mapping(source = "price",                                                          target = "price")
  @Mapping(expression = "java(IdConverter.convertId(product.getStore().getId()))",    target = "storeId")
  ProductResponse mapToDto(Product product);
  List<ProductResponse> mapToDto(List<Product> product);

  @Mapping(expression = "java(new Identity(product.getId()))",                        target = "id")
  Product mapToDomain(ProductResponse product);
  List<Product> mapToDomain(List<ProductResponse> product);
}
