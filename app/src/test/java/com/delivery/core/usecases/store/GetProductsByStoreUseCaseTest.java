package com.delivery.core.usecases.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.repositories.IStoreRepository;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetProductsByStoreUseCaseTest {

    @InjectMocks
    private GetProductsByStoreUseCase useCase;

    @Mock
    private IStoreRepository repository;

    @Test
    public void getProductsByStoreIdentityReturnsProductsWhenStoreFound() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        Store store = product.getStore();
        GetProductsByStoreUseCase.InputValues input =
                GetProductsByStoreUseCase.InputValues.builder().id(store.getId()).build();

        // and
        doReturn(Collections.singletonList(product))
                .when(repository)
                .getProductsById(eq(store.getId()));

        // when
        List<Product> actual = useCase.execute(input).getProducts();

        // then
        assertThat(actual).containsOnly(product);
    }

    @Test
    public void getProductsByStoreIdentityThrowsNotFoundWhenStoreNotFound() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        GetProductsByStoreUseCase.InputValues input =
            GetProductsByStoreUseCase.InputValues.builder().id(id).build();

        // and
        doReturn(Collections.emptyList())
                .when(repository)
                .getProductsById(eq(id));

        // then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("No store found by identity: " + id.getNumber());
    }
}