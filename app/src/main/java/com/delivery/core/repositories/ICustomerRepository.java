package com.delivery.core.repositories;

import com.delivery.core.domain.Customer;
import com.delivery.database.entities.CustomerDb;
import java.util.Optional;

public interface ICustomerRepository {
    Customer persist(Customer customer);

    boolean existsByEmail(String email);

    Optional<CustomerDb> findByEmail(String email);

    Optional<CustomerDb> findById(Long id);
}
