package com.delivery.core.usecases.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.repositories.IProductRepository;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetProductUseCaseTest {

    @InjectMocks
    private GetProductUseCase useCase;

    @Mock
    private IProductRepository repository;

    @Test
    public void executeThrowsException() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        GetProductUseCase.InputValues input =
                GetProductUseCase.InputValues.builder().id(id).build();

        // and
        doReturn(Optional.empty())
                .when(repository)
                .getById(id);

        // then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Product " + id.getNumber() + " not found");
    }

    @Test
    public void executeReturnsProduct() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        GetProductUseCase.InputValues input =
                GetProductUseCase.InputValues.builder().id(product.getId()).build();

        // and
        doReturn(Optional.of(product))
                .when(repository)
                .getById(product.getId());

        // when
        Product actual = useCase.execute(input).getProduct();

        // then
        assertThat(actual).isEqualTo(product);
    }
}