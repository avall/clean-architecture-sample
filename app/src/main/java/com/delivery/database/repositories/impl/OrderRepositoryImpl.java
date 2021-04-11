package com.delivery.database.repositories.impl;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Order;
import com.delivery.core.mappers.OrderDomainDbMapper;
import com.delivery.core.repositories.IOrderRepository;
import com.delivery.database.entities.OrderDb;
import com.delivery.database.repositories.DbOrderRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements IOrderRepository {
    private final DbOrderRepository repository;
    private final OrderDomainDbMapper  orderDomainDbMapper;


    @Override
    public Order persist(Order order) {
        OrderDb orerDb = orderDomainDbMapper.mapToDb(order);
        orerDb.getOrderItems().stream().forEach(o -> o.setOrder(orerDb));

        return orderDomainDbMapper.mapToDomain(repository.save(orerDb));
    }

    @Override
    public Optional<Order> getById(Identity id) {
        return repository
                .findById(id.getNumber())
                .map(o -> orderDomainDbMapper.mapToDomain(o));
    }
}
