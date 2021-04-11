package com.delivery.core.mappers;

import com.delivery.core.domain.Cousine;
import com.delivery.database.entities.CousineDb;
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
public interface CousineDomainDbMapper extends BaseDomainDbMapper<Cousine, CousineDb> {
  @Mapping(expression = "java(IdConverter.convertId(cousine.getId()))",     target = "id")
  @Mapping(source = "name",     target = "name")
  CousineDb mapToDb(Cousine cousine);
  List<CousineDb> mapToDb(List<Cousine> cousine);

  @Mapping(expression = "java(new Identity(cousine.getId()))",     target = "id")
  @Mapping(source = "name",     target = "name")
  Cousine mapToDomain(CousineDb cousine);
  List<Cousine> mapToDomain(List<CousineDb> cousine);
}
