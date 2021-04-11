package com.delivery.presenter.mappers.domainDto;

import java.util.List;

/**
 * Convert from Domain(F) to Dto(T)
 */
public interface BaseDomainDtoMapper<F,T> {
  /**
   * Maps from Domain object to Db object.
   * @param from  source
   * @return  to  target
   */
  T mapToDto(F from);

  /**
   * Maps list of objects
   * @param eList the list
   * @return Transformed list.
   */
  List<T> mapToDto(List<F> eList);

  /**
   * Maps from Db object to Domain object.
   * @param from  source
   * @return  to  target
   */
  F mapToDomain(T from);

  /**
   * Maps list of objects
   * @param eList the list
   * @return Transformed list.
   */
  List<F> mapToDomain(List<T> eList);

}

