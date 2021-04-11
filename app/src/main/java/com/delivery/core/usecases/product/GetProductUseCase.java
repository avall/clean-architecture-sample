package com.delivery.core.usecases.product;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.repositories.IProductRepository;
import com.delivery.core.usecases.UseCase;
import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class GetProductUseCase implements UseCase<GetProductUseCase.InputValues, GetProductUseCase.OutputValues> {
    private IProductRepository repository;

    public GetProductUseCase(IProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Identity id = input.getId();

        return repository
                .getById(id)
                .map(OutputValues::new)
                .orElseThrow(() -> new NotFoundException("Product %s not found", id.getNumber()));
    }

    @Value
    @Builder
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    @Builder
    public static class OutputValues implements UseCase.OutputValues {
        private final Product product;
    }
}
