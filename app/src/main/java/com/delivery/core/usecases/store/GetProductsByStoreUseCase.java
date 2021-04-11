package com.delivery.core.usecases.store;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.repositories.IStoreRepository;
import com.delivery.core.usecases.UseCase;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class GetProductsByStoreUseCase implements UseCase<GetProductsByStoreUseCase.InputValues, GetProductsByStoreUseCase.OutputValues> {
    private IStoreRepository repository;

    public GetProductsByStoreUseCase(IStoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues inputValues) {
        Identity id = inputValues.getId();

        List<Product> products = repository.getProductsById(id);

        if (products.isEmpty()) {
            throw new NotFoundException("No store found by identity: " + id.getNumber());
        }

        return OutputValues.builder().products(products).build();
    }

    @Value
    @Builder
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    @Builder
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Product> products;
    }
}
