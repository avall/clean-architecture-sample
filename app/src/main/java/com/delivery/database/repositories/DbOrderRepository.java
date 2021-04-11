package com.delivery.database.repositories;

import com.delivery.database.entities.OrderDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbOrderRepository extends JpaRepository<OrderDb, Long> {
}
