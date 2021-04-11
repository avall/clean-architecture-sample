package com.delivery.presenter.mappers.domainDto;

import com.delivery.core.domain.Cousine;
import com.delivery.database.entities.IdConverter;
import com.delivery.dto.CousineResponse;
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
public interface CousineDomainDtoMapper extends BaseDomainDtoMapper<Cousine, CousineResponse> {
  @Mapping(expression = "java(cousine.getId().getNumber())",     target = "id")
  @Mapping(source = "name",     target = "name")
  CousineResponse mapToDto(Cousine cousine);
  List<CousineResponse> mapToDto(List<Cousine> cousine);

  @Mapping(expression = "java(new Identity(cousine.getId()))",     target = "id")
  @Mapping(source = "name",     target = "name")
  Cousine mapToDomain(CousineResponse cousine);
  List<Cousine> mapToDomain(List<CousineResponse> cousine);
}
