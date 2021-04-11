package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Cousine;
import com.delivery.core.repositories.ICousineRepository;
import com.delivery.core.usecases.UseCase;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class SearchCousineByNameUseCase implements UseCase<SearchCousineByNameUseCase.InputValues, SearchCousineByNameUseCase.OutputValues> {

    private ICousineRepository repository;

    public SearchCousineByNameUseCase(ICousineRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        return OutputValues.builder().cousines(repository.searchByName(input.getSearchText())).build();
    }

    @Value
    @Builder
    public static class InputValues implements UseCase.InputValues {
        private final String searchText;
    }

    @Value
    @Builder
    public static class OutputValues implements UseCase.OutputValues {
        private final List<Cousine> cousines;
    }
}
