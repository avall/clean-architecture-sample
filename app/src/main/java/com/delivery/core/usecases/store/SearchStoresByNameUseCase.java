package com.delivery.core.usecases.store;

import com.delivery.core.domain.Store;
import com.delivery.core.repositories.IStoreRepository;
import com.delivery.core.usecases.UseCase;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class SearchStoresByNameUseCase implements UseCase<SearchStoresByNameUseCase.InputValues, SearchStoresByNameUseCase.OutputValues> {
    private IStoreRepository repository;

    public SearchStoresByNameUseCase(IStoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        return OutputValues.builder().stores(repository.searchByName(input.getSearchText())).build();
    }

    @Value
    @Builder
    public static class InputValues implements UseCase.InputValues {
        private final String searchText;
    }

    @Value
    @Builder
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Store> stores;
    }
}

