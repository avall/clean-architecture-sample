package com.delivery.presenter.rest.api.cousine;

import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.cousine.GetAllCousinesUseCase;
import com.delivery.core.usecases.cousine.GetStoresByCousineUseCase;
import com.delivery.core.usecases.cousine.SearchCousineByNameUseCase;
import com.delivery.dto.CousineResponse;
import com.delivery.dto.StoreResponse;
import com.delivery.presenter.mappers.domainDto.CousineDomainDtoMapper;
import com.delivery.presenter.mappers.domainDto.StoreDomainDtoMapper;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@RequiredArgsConstructor
public class CousineController implements CousineResource {
    private final UseCaseExecutor useCaseExecutor;
    private final GetAllCousinesUseCase getAllCousinesUseCase;
    private final GetStoresByCousineUseCase getStoresByCousineUseCase;
    private final SearchCousineByNameUseCase searchCousineByNameUseCase;
    private final CousineDomainDtoMapper cousineDomainDtoMapper;
    private final StoreDomainDtoMapper storeDomainDtoMapper;

    @Override
    public CompletableFuture<List<StoreResponse>> getStoresByCousineId(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getStoresByCousineUseCase,
                GetStoresByCousineUseCase.InputValues.builder().id(new Identity(id)).build(),
                (outputValues) -> storeDomainDtoMapper.mapToDto(outputValues.getStores()));
    }

    @Override
    public CompletableFuture<List<CousineResponse>> getAllCousines() {
        return useCaseExecutor.execute(
                getAllCousinesUseCase,
                GetAllCousinesUseCase.InputValues.builder().build(),
                (outputValues) -> cousineDomainDtoMapper.mapToDto(outputValues.getCousines()));
    }

    @Override
    public CompletableFuture<List<CousineResponse>> getAllCousinesByNameMatching(@PathVariable String text) {
        return useCaseExecutor.execute(
                searchCousineByNameUseCase,
                SearchCousineByNameUseCase.InputValues.builder().searchText(text).build(),
                (outputValues) -> cousineDomainDtoMapper.mapToDto(outputValues.getCousines()));
    }
}
