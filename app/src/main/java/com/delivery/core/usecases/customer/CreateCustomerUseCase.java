package com.delivery.core.usecases.customer;

import com.delivery.core.domain.Customer;
import com.delivery.core.domain.EmailAlreadyUsedException;
import com.delivery.core.domain.Identity;
import com.delivery.core.repositories.ICustomerRepository;
import com.delivery.core.usecases.UseCase;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCustomerUseCase implements UseCase<CreateCustomerUseCase.InputValues, CreateCustomerUseCase.OutputValues> {
    private final ICustomerRepository repository;

    @Override
    public OutputValues execute(InputValues input) {
        if (repository.existsByEmail(input.getEmail())) {
            throw new EmailAlreadyUsedException("Email address already in use!");
        }

        Customer customer = Customer.builder()
            .id(Identity.nothing())
            .name(input.getName())
            .email(input.getEmail())
            .address(input.getAddress())
            .password(input.getPassword())
            .build();


        return OutputValues.builder().customer(repository.persist(customer)).build();
    }

    @Value
    @Builder
    public static class InputValues implements UseCase.InputValues{
        private final String name;
        private final String email;
        private final String address;
        private final String password;
    }

    @Value
    @Builder
    public static class OutputValues implements UseCase.OutputValues {
        private final Customer customer;
    }
}
