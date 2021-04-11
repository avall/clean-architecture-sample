package com.delivery.core.usecases.order;

import com.delivery.core.domain.Order;
import com.delivery.core.repositories.IOrderRepository;
import org.springframework.stereotype.Component;

@Component
public class PayOrderUseCase extends UpdateOrderUseCase {
    public PayOrderUseCase(IOrderRepository repository) {
        super(repository);
    }

    @Override
    protected Order updateStatus(Order order) {
        return repository.persist(order.pay());
    }
}
