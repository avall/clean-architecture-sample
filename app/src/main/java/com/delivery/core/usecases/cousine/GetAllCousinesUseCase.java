package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Cousine;
import com.delivery.core.repositories.ICousineRepository;
import com.delivery.core.usecases.UseCase;
import com.delivery.core.usecases.cousine.GetAllCousinesUseCase.InputValues;
import com.delivery.core.usecases.cousine.GetAllCousinesUseCase.OutputValues;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class GetAllCousinesUseCase implements UseCase<InputValues, OutputValues>  {
    private ICousineRepository repository;

    public GetAllCousinesUseCase(ICousineRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        return OutputValues.builder().cousines(repository.getAll()).build();
    }

    @Value
    @Builder
    public static class InputValues implements UseCase.InputValues {
    }

    @Value
    @Builder
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Cousine> cousines;
    }
}
