package com.delivery.core.usecases.product;

import com.delivery.core.domain.Product;
import com.delivery.core.repositories.IProductRepository;
import com.delivery.core.usecases.UseCase;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class SearchProductsByNameOrDescriptionUseCase implements UseCase<SearchProductsByNameOrDescriptionUseCase.InputValues, SearchProductsByNameOrDescriptionUseCase.OutputValues> {
    private IProductRepository repository;

    public SearchProductsByNameOrDescriptionUseCase(IProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        return OutputValues.builder().products(repository.searchByNameOrDescription(input.getSearchText())).build();
    }

    @Value
    @Builder
    public static class InputValues implements UseCase.InputValues {
        private final String searchText;
    }

    @Value
    @Builder
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Product> products;
    }
}
