package com.delivery.presenter.mappers.domainDto;

import com.delivery.core.domain.Store;
import com.delivery.database.entities.IdConverter;
import com.delivery.dto.StoreResponse;
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
public interface StoreDomainDtoMapper extends BaseDomainDtoMapper<Store, StoreResponse> {
  @Mapping(expression = "java(IdConverter.convertId(store.getId()))",                 target = "id")
  @Mapping(source     = "name",                                                       target = "name")
  @Mapping(source     = "address",                                                    target = "address")
  @Mapping(expression = "java(IdConverter.convertId(store.getCousine().getId()))",    target = "cousineId")
  StoreResponse mapToDto(Store store);
  List<StoreResponse> mapToDto(List<Store> store);

  @Mapping(expression = "java(new Identity(store.getId()))",                          target = "id")
  @Mapping(source = "name",                                                           target = "name")
  @Mapping(source = "address",                                                        target = "address")
  Store mapToDomain(StoreResponse store);
  List<Store> mapToDomain(List<StoreResponse> store);
}
