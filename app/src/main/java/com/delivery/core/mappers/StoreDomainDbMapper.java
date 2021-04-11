package com.delivery.core.mappers;

import com.delivery.core.domain.Store;
import com.delivery.database.entities.IdConverter;
import com.delivery.database.entities.StoreDb;
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
        CousineDomainDbMapper.class
    }
)
public interface StoreDomainDbMapper extends BaseDomainDbMapper<Store, StoreDb> {
  @Mapping(expression = "java(IdConverter.convertId(store.getId()))",     target = "id")
  @Mapping(source = "name",     target = "name")
  @Mapping(source = "address",  target = "address")
  @Mapping(source = "cousine",  target = "cousine")
  StoreDb mapToDb(Store store);
  List<StoreDb> mapToDb(List<Store> store);

  @Mapping(expression = "java(new Identity(store.getId()))",     target = "id")
  @Mapping(source = "name",     target = "name")
  @Mapping(source = "address",  target = "address")
  @Mapping(source = "cousine",  target = "cousine")
  Store mapToDomain(StoreDb store);
  List<Store> mapToDomain(List<StoreDb> store);
}
