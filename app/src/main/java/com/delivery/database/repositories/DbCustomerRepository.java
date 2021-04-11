package com.delivery.database.repositories;

import com.delivery.database.entities.CustomerDb;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbCustomerRepository extends JpaRepository<CustomerDb, Long> {
    boolean existsByEmail(String email);

    Optional<CustomerDb> findByEmail(String email);
}
