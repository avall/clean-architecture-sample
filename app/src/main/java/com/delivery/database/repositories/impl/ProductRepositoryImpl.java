package com.delivery.database.repositories.impl;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Product;
import com.delivery.core.mappers.ProductDomainDbMapper;
import com.delivery.core.repositories.IProductRepository;
import com.delivery.database.repositories.DbProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements IProductRepository {
    private final DbProductRepository repository;
    private final ProductDomainDbMapper productDomainDbMapper;

    @Override
    public List<Product> getAll() {
        return repository
                .findAll()
                .stream()
                .map(p -> productDomainDbMapper.mapToDomain(p))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> getById(Identity id) {
        return repository
                .findById(id.getNumber())
                .map(p -> productDomainDbMapper.mapToDomain(p));
    }

    @Override
    public List<Product> searchByNameOrDescription(String searchText) {
        return repository
                .findByNameContainingOrDescriptionContainingAllIgnoreCase(searchText, searchText)
                .stream()
                .map(p -> productDomainDbMapper.mapToDomain(p))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> searchProductsByStoreAndProductsId(Identity storeId, List<Identity> productsId) {
        return repository
                .findByStoreIdAndIdIsIn(storeId.getNumber(), createListOfLong(productsId))
                .stream()
                .map(p -> productDomainDbMapper.mapToDomain(p))
                .collect(Collectors.toList());
    }

    private List<Long> createListOfLong(List<Identity> productsId) {
        return productsId
                .stream()
                .map(Identity::getNumber)
                .collect(Collectors.toList());
    }
}
