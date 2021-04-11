package com.delivery.database.repositories.impl;

import com.delivery.core.domain.Customer;
import com.delivery.core.mappers.CustomerDomainDbMapper;
import com.delivery.core.repositories.ICustomerRepository;
import com.delivery.database.entities.CustomerDb;
import com.delivery.database.repositories.DbCustomerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements ICustomerRepository {
    private final DbCustomerRepository repository;
    private final CustomerDomainDbMapper customerDomainDbMapper;

    @Override
    public Customer persist(Customer customer) {
        final CustomerDb customerData = customerDomainDbMapper.mapToDb(customer);
        return customerDomainDbMapper.mapToDomain(repository.save(customerData));
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Optional<CustomerDb> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<CustomerDb> findById(Long id) {
        return repository.findById(id);
    }
}
