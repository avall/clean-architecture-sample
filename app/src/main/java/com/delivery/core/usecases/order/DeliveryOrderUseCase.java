package com.delivery.core.usecases.order;

import com.delivery.core.domain.Order;
import com.delivery.core.repositories.IOrderRepository;
import org.springframework.stereotype.Component;

@Component
public class DeliveryOrderUseCase extends UpdateOrderUseCase {
    public DeliveryOrderUseCase(IOrderRepository repository) {
        super(repository);
    }

    @Override
    protected Order updateStatus(Order order) {
        order.delivery();

        return repository.persist(order.delivery());
    }
}
