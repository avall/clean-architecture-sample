package com.delivery.core.repositories;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Store;
import java.util.List;
import java.util.Optional;

public interface IStoreRepository {
    List<Store> getAll();

    List<Store> searchByName(String searchText);

    Optional<Store> getById(Identity id);

    List<Product> getProductsById(Identity id);

    List<Store> getStoresById(Identity id);

}
