package com.delivery.presenter.rest.api.order;

import static com.delivery.core.entities.TestCoreEntityGenerator.randomId;
import static com.delivery.core.entities.TestCoreEntityGenerator.randomQuantity;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.TestEntityGenerator;
import com.delivery.core.domain.Customer;
import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.order.CreateOrderUseCase;
import com.delivery.dto.OrderRequest;
import com.delivery.dto.OrderRequestItem;
import com.delivery.presenter.mappers.inputOutputDto.CreateOrderInputMapper;
import com.delivery.presenter.usecases.security.UserPrincipal;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

public class InputValuesMapperTest {

    @Test
    public void mapReturnsCreateOrderInputMapper() {
        // given
        UserPrincipal userPrincipal = TestEntityGenerator.randomUserPrincipal();
        Customer customer = Customer.builder()
                .id(new Identity(userPrincipal.getId()))
                .name(userPrincipal.getUsername())
                .email(userPrincipal.getEmail())
                .address(userPrincipal.getAddress())
                .password(userPrincipal.getPassword())
                .build();

        Identity orderItemId = randomId();
        Integer orderItemQuantity = randomQuantity();
        OrderRequestItem orderItem = new OrderRequestItem(orderItemId.getNumber(), orderItemQuantity);

        Identity storeId = randomId();
        OrderRequest orderRequest = new OrderRequest(storeId.getNumber(), singletonList(orderItem));

        // when
        CreateOrderUseCase.InputValues actual = Mappers.getMapper(CreateOrderInputMapper.class)
            .map(orderRequest, userPrincipal);

        // then
        assertThat(actual.getCustomer()).isEqualTo(customer);
        assertThat(actual.getStoreId()).isEqualTo(storeId);
        assertThat(actual.getOrderItems()).extracting("id").containsOnly(orderItemId);
        assertThat(actual.getOrderItems()).extracting("quantity").containsOnly(orderItemQuantity);
    }
}