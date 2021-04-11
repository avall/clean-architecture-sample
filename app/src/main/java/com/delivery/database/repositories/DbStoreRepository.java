package com.delivery.database.repositories;

import com.delivery.database.entities.ProductDb;
import com.delivery.database.entities.StoreDb;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DbStoreRepository extends JpaRepository<StoreDb, Long> {
    List<StoreDb> findByNameContainingIgnoreCase(String name);

    @Query("select p from store s join s.products p where s.id = ?1")
    List<ProductDb> findProductsById(Long id);

    @Query("select s from cousine c join c.stores s where c.id = ?1")
    List<StoreDb> findStoresById(Long id);
}
