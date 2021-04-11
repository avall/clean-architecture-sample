package com.delivery.presenter.rest.api.store;

import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.store.GetAllStoresUseCase;
import com.delivery.core.usecases.store.GetProductsByStoreUseCase;
import com.delivery.core.usecases.store.GetStoreUseCase;
import com.delivery.core.usecases.store.SearchStoresByNameUseCase;
import com.delivery.dto.ProductResponse;
import com.delivery.dto.StoreResponse;
import com.delivery.presenter.mappers.domainDto.ProductDomainDtoMapper;
import com.delivery.presenter.mappers.domainDto.StoreDomainDtoMapper;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@RequiredArgsConstructor
public class StoreController implements StoreResource {

    private final UseCaseExecutor useCaseExecutor;
    private final GetAllStoresUseCase getAllStoresUseCase;
    private final SearchStoresByNameUseCase searchStoresByNameUseCase;
    private final GetStoreUseCase getStoreUseCase;
    private final GetProductsByStoreUseCase getProductsByStoreUseCase;
    private final StoreDomainDtoMapper storeDomainDtoMapper;
    private final ProductDomainDtoMapper productDomainDtoMapper;

    @Override
    public CompletableFuture<List<StoreResponse>> getAll() {
        return useCaseExecutor.execute(
                getAllStoresUseCase,
                GetAllStoresUseCase.InputValues.builder().build(),
                (outputValues) -> storeDomainDtoMapper.mapToDto(outputValues.getStores()));
    }

    @Override
    public CompletableFuture<List<StoreResponse>> getAllStoresByNameMatching(@PathVariable String text) {
        return useCaseExecutor.execute(
                searchStoresByNameUseCase,
                SearchStoresByNameUseCase.InputValues.builder().searchText(text).build(),
                (outputValues) -> storeDomainDtoMapper.mapToDto(outputValues.getStores()));
    }

    @Override
    public CompletableFuture<StoreResponse> getStoreByIdentity(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getStoreUseCase,
                GetStoreUseCase.InputValues.builder().id(new Identity(id)).build(),
                (outputValues) -> storeDomainDtoMapper.mapToDto(outputValues.getStore()));
    }

    @Override
    public CompletableFuture<List<ProductResponse>> getProductsBy(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getProductsByStoreUseCase,
                GetProductsByStoreUseCase.InputValues.builder().id(new Identity(id)).build(),
                (outputValues) -> productDomainDtoMapper.mapToDto(outputValues.getProducts()));
    }
}
