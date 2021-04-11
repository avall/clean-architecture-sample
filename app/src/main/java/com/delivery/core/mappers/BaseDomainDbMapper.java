package com.delivery.core.mappers;

import com.delivery.core.domain.BaseDomainEntity;
import com.delivery.database.entities.BaseDbEntity;
import java.util.List;

/**
 * Convert from Domain(F) to Db(T)
 */
public interface BaseDomainDbMapper<F extends BaseDomainEntity, T extends BaseDbEntity> {
  /**
   * Maps from Domain object to Db object.
   * @param from  source
   * @return  to  target
   */
  T mapToDb(F from);

  /**
   * Maps list of objects
   * @param eList the list
   * @return Transformed list.
   */
  List<T> mapToDb(List<F> eList);

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

