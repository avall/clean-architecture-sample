package com.delivery.presenter.rest.api.product;

import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.product.GetAllProductsUseCase;
import com.delivery.core.usecases.product.GetProductUseCase;
import com.delivery.core.usecases.product.SearchProductsByNameOrDescriptionUseCase;
import com.delivery.dto.ProductResponse;
import com.delivery.presenter.mappers.domainDto.ProductDomainDtoMapper;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@RequiredArgsConstructor
public class ProductController implements ProductResource {
    private final UseCaseExecutor useCaseExecutor;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final GetProductUseCase getProductUseCase;
    private final SearchProductsByNameOrDescriptionUseCase searchProductsByNameOrDescriptionUseCase;
    private final ProductDomainDtoMapper productDomainDtoMapper;

    @Override
    public CompletableFuture<List<ProductResponse>> getAllProducts() {
        return useCaseExecutor.execute(
                getAllProductsUseCase,
                GetAllProductsUseCase.InputValues.builder().build(),
                (outputValues) -> productDomainDtoMapper.mapToDto(outputValues.getProducts()));
    }

    @Override
    public CompletableFuture<ProductResponse> getByIdentity(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getProductUseCase,
                GetProductUseCase.InputValues.builder().id(new Identity(id)).build(),
                (outputValues) -> productDomainDtoMapper.mapToDto(outputValues.getProduct()));
    }

    @Override
    public CompletableFuture<List<ProductResponse>> getByMatchingName(@PathVariable String text) {
        return useCaseExecutor.execute(
                searchProductsByNameOrDescriptionUseCase,
                SearchProductsByNameOrDescriptionUseCase.InputValues.builder().searchText(text).build(),
                (outputValues) -> productDomainDtoMapper.mapToDto(outputValues.getProducts()));
    }
}
