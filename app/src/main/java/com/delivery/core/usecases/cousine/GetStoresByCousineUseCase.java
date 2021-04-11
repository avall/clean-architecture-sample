package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Store;
import com.delivery.core.repositories.IStoreRepository;
import com.delivery.core.usecases.UseCase;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class GetStoresByCousineUseCase implements UseCase<GetStoresByCousineUseCase.InputValues, GetStoresByCousineUseCase.OutputValues> {
    private IStoreRepository storeRepository;

    public GetStoresByCousineUseCase(IStoreRepository repository) {
        this.storeRepository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Identity id = input.getId();

        List<Store> stores = storeRepository.getStoresById(id);

        if (stores.isEmpty()) {
            throw new NotFoundException("Cousine %s not found", id.getNumber());
        }

        return OutputValues.builder().stores(stores).build();
    }

    @Value
    @Builder
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    @Builder
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Store> stores;
    }
}
