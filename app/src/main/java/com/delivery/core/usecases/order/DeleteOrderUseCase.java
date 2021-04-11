package com.delivery.core.usecases.order;

import com.delivery.core.domain.Order;
import com.delivery.core.repositories.IOrderRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteOrderUseCase extends UpdateOrderUseCase {

    public DeleteOrderUseCase(IOrderRepository repository) {
        super(repository);
    }

    @Override
    protected Order updateStatus(Order order) {
        return order.delete();
    }
}
