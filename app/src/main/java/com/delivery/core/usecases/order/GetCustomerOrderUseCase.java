package com.delivery.core.usecases.order;

import com.delivery.core.domain.Customer;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Order;
import com.delivery.core.usecases.UseCase;
import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class GetCustomerOrderUseCase implements UseCase<GetCustomerOrderUseCase.InputValues, GetCustomerOrderUseCase.OutputValues> {
    private GetOrderUseCase getOrderUseCase;

    public GetCustomerOrderUseCase(GetOrderUseCase getOrderUseCase) {
        this.getOrderUseCase = getOrderUseCase;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Order order = getOrderUseCase
                .execute(GetOrderUseCase.InputValues.builder().id(input.getId()).build())
                .getOrder();

        return OutputValues.builder().customer(order.getCustomer()).build();
    }

    @Value
    @Builder
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    @Builder
    public static class OutputValues implements UseCase.OutputValues {
        private final Customer customer;
    }
}
