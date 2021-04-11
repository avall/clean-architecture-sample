package com.delivery.core.usecases.order;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Order;
import com.delivery.core.repositories.IOrderRepository;
import com.delivery.core.usecases.UseCase;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public abstract class UpdateOrderUseCase implements UseCase<UpdateOrderUseCase.InputValues, UpdateOrderUseCase.OutputValues> {
    protected final IOrderRepository repository;

    @Override
    public OutputValues execute(InputValues input) {
        final Identity id = input.getId();

        return this.repository
                .getById(id)
                .map(this::updateStatus)
                .map(this::persistAndReturn)
                .orElseThrow(() -> new NotFoundException("Order %s not found", id));
    }

    protected abstract Order updateStatus(Order order);

    private OutputValues persistAndReturn(Order order) {
        return OutputValues.builder().order(this.repository.persist(order)).build();
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
