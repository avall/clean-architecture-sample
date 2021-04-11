package com.delivery.core.repositories;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Order;
import java.util.Optional;

public interface IOrderRepository {

    Order persist(Order order);

    Optional<Order> getById(Identity id);
}
