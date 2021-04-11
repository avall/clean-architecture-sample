package com.delivery.core.usecases.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.delivery.core.domain.Product;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.repositories.IProductRepository;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetAllProductsUseCaseTest {

    @InjectMocks
    private GetAllProductsUseCase useCase;

    @Mock
    private IProductRepository repository;

    @Test
    public void executeReturnsAllProducts() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        GetAllProductsUseCase.InputValues input =
                GetAllProductsUseCase.InputValues.builder().build();

        // and
        doReturn(Collections.singletonList(product))
                .when(repository)
                .getAll();

        // when
        List<Product> actual = useCase.execute(input).getProducts();

        // then
        assertThat(actual).containsOnly(product);
    }
}