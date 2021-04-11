package com.delivery.core.usecases.store;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Store;
import com.delivery.core.repositories.IStoreRepository;
import com.delivery.core.usecases.UseCase;
import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class GetStoreUseCase implements UseCase<GetStoreUseCase.InputValues, GetStoreUseCase.OutputValues> {
    private IStoreRepository repository;

    public GetStoreUseCase(IStoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Identity id = input.getId();

        return repository
                .getById(id)
                .map(OutputValues::new)
                .orElseThrow(() -> new NotFoundException("Store %s not found", id.getNumber()));
    }

    @Value
    @Builder
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    @Builder
    public static class OutputValues implements UseCase.OutputValues {
        private final Store store;
    }
}
