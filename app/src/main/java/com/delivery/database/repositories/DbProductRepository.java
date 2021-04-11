package com.delivery.database.repositories;

import com.delivery.database.entities.ProductDb;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbProductRepository extends JpaRepository<ProductDb, Long> {
    List<ProductDb> findByNameContainingOrDescriptionContainingAllIgnoreCase(String name, String description);

    List<ProductDb> findByStoreIdAndIdIsIn(Long id, List<Long> ids);
}
