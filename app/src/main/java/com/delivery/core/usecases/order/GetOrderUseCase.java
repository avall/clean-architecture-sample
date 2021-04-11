package com.delivery.core.usecases.order;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Order;
import com.delivery.core.repositories.IOrderRepository;
import com.delivery.core.usecases.UseCase;
import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class GetOrderUseCase implements UseCase<GetOrderUseCase.InputValues, GetOrderUseCase.OutputValues> {
    private IOrderRepository repository;

    public GetOrderUseCase(IOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        final Identity id = input.getId();

        return repository.getById(id)
                .map(OutputValues::new)
                .orElseThrow(() -> new NotFoundException("Order %s not found", id.getNumber()));
    }

    @Value
    @Builder
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    @Builder
    public static class OutputValues implements UseCase.OutputValues {
        private final Order order;
    }
}
