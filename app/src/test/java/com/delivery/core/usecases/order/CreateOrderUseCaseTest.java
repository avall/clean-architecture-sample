package com.delivery.core.usecases.order;

import static com.delivery.core.entities.TestCoreEntityGenerator.randomId;
import static com.delivery.core.entities.TestCoreEntityGenerator.randomQuantity;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import com.delivery.core.domain.Customer;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Order;
import com.delivery.core.domain.Product;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.repositories.IOrderRepository;
import com.delivery.core.usecases.product.GetProductsByStoreAndProductsIdUseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateOrderUseCaseTest {

    @InjectMocks
    private CreateOrderUseCase useCase;

    @Mock
    private GetProductsByStoreAndProductsIdUseCase getProductsByStoreAndProductsIdUseCase;

    @Mock
    private IOrderRepository orderRepository;

    @Test
    public void executeShouldCreateAndReturnOrder() {
        // given
        Identity storeId = randomId();
        CreateOrderUseCase.InputItem useCaseInputItem =
                new CreateOrderUseCase.InputItem(randomId(), randomQuantity());

        GetProductsByStoreAndProductsIdUseCase.InputValues getProductsInput =
                GetProductsByStoreAndProductsIdUseCase.InputValues.builder()
                .storeId(storeId)
                .productsId(singletonList(useCaseInputItem.getId()))
                .build();

        Product product = TestCoreEntityGenerator.randomProduct();
        product.setId(useCaseInputItem.getId());

        GetProductsByStoreAndProductsIdUseCase.OutputValues getProductsOutput =
                GetProductsByStoreAndProductsIdUseCase.OutputValues.builder()
                .products(singletonList(product)).build();

        Customer customer = TestCoreEntityGenerator.randomCustomer();

        CreateOrderUseCase.InputValues useCaseInput =
                CreateOrderUseCase.InputValues.builder()
                .customer(customer)
                .storeId(storeId)
                .orderItems(singletonList(useCaseInputItem))
                .build();

        Order expected = TestCoreEntityGenerator.randomOrder();

        // and
        doReturn(getProductsOutput)
                .when(getProductsByStoreAndProductsIdUseCase)
                .execute(eq(getProductsInput));

        // and
        doReturn(expected)
                .when(orderRepository)
                .persist(any(Order.class));

        // when
        Order actual = useCase.execute(useCaseInput).getOrder();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}