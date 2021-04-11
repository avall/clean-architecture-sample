package com.delivery.core.mappers;

import com.delivery.core.domain.Product;
import com.delivery.database.entities.IdConverter;
import com.delivery.database.entities.ProductDb;
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
        StoreDomainDbMapper.class
    }
)
public interface ProductDomainDbMapper extends BaseDomainDbMapper<Product, ProductDb> {
  @Mapping(expression = "java(IdConverter.convertId(product.getId()))",     target = "id")
  @Mapping(source = "name",         target = "name")
  @Mapping(source = "description",  target = "description")
  @Mapping(source = "price",        target = "price")
  @Mapping(source = "store",        target = "store")
  ProductDb mapToDb(Product product);
  List<ProductDb> mapToDb(List<Product> product);

  @Mapping(expression = "java(new Identity(product.getId()))",     target = "id")
  @Mapping(source = "name",         target = "name")
  @Mapping(source = "description",  target = "description")
  @Mapping(source = "price",        target = "price")
  @Mapping(source = "store",        target = "store")
  Product mapToDomain(ProductDb product);
  List<Product> mapToDomain(List<ProductDb> product);
}
