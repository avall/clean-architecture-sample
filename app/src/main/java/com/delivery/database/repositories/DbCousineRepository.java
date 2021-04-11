package com.delivery.database.repositories;

import com.delivery.database.entities.CousineDb;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbCousineRepository extends JpaRepository<CousineDb, Long> {
    List<CousineDb> findByNameContainingIgnoreCase(String search);

}
