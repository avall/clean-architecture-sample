package com.delivery.database.repositories.impl;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Store;
import com.delivery.core.mappers.ProductDomainDbMapper;
import com.delivery.core.mappers.StoreDomainDbMapper;
import com.delivery.core.repositories.IStoreRepository;
import com.delivery.database.repositories.DbStoreRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements IStoreRepository {
    private final DbStoreRepository repository;
    private final StoreDomainDbMapper storeDomainDbMapper;
    private final ProductDomainDbMapper productDomainDbMapper;

    @Override
    public List<Store> getAll() {
        return repository
                .findAll()
                .parallelStream()
                .map(s -> storeDomainDbMapper.mapToDomain(s))
                .collect(Collectors.toList());
    }

    @Override
    public List<Store> searchByName(String searchText) {
        return repository
                .findByNameContainingIgnoreCase(searchText)
                .parallelStream()
                .map(s -> storeDomainDbMapper.mapToDomain(s))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Store> getById(Identity id) {
        return repository
                .findById(id.getNumber())
                .map(s -> storeDomainDbMapper.mapToDomain(s));
    }

    @Override
    public List<Product> getProductsById(Identity id) {
        return repository
                .findProductsById(id.getNumber())
                .stream()
                .map(p -> productDomainDbMapper.mapToDomain(p))
                .collect(Collectors.toList());
    }

    @Override
    public List<Store> getStoresById(Identity id) {
        return repository
            .findStoresById(id.getNumber())
            .parallelStream()
            .map(s -> storeDomainDbMapper.mapToDomain(s))
            .collect(Collectors.toList());
    }
}
