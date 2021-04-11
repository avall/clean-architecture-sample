package com.delivery.core.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Order;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.repositories.IOrderRepository;
import com.delivery.core.usecases.order.GetOrderUseCase;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetOrderUseCaseTest {

    @InjectMocks
    private GetOrderUseCase useCase;

    @Mock
    private IOrderRepository repository;

    @Test
    public void executeThrowExceptionWhenOrderIsNotFound() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        GetOrderUseCase.InputValues input = GetOrderUseCase.InputValues.builder().id(id).build();

        // and
        doReturn(Optional.empty())
                .when(repository)
                .getById(eq(id));

        // then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Order " + id.getNumber() + " not found");
    }

    @Test
    public void executeReturnsOrderWhenOrderIsFound() {
        // given
        Order expected = TestCoreEntityGenerator.randomOrder();
        GetOrderUseCase.InputValues input = GetOrderUseCase.InputValues.builder()
            .id(expected.getId()).build();

        // and
        doReturn(Optional.of(expected))
                .when(repository)
                .getById(eq(expected.getId()));

        // when
        Order actual = useCase.execute(input).getOrder();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}