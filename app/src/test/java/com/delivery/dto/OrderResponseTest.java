package com.delivery.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.core.domain.Order;
import com.delivery.core.domain.OrderItem;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.presenter.mappers.domainDto.CousineDomainDtoMapper;
import com.delivery.presenter.mappers.domainDto.CustomerDomainDtoMapper;
import com.delivery.presenter.mappers.domainDto.OrderDomainDtoMapper;
import com.delivery.presenter.mappers.domainDto.OrderItemDomainDtoMapper;
import com.delivery.presenter.mappers.domainDto.ProductDomainDtoMapper;
import com.delivery.presenter.mappers.domainDto.StoreDomainDtoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderResponseTest {
    @InjectMocks private OrderItemDomainDtoMapper orderItemDomainDtoMapper = Mockito.spy(Mappers.getMapper(OrderItemDomainDtoMapper.class));
    @InjectMocks private CustomerDomainDtoMapper customerDomainDtoMapper = Mockito.spy(Mappers.getMapper(CustomerDomainDtoMapper.class));
    @InjectMocks private StoreDomainDtoMapper storeDomainDtoMapper = Mockito.spy(Mappers.getMapper(StoreDomainDtoMapper.class));
    @InjectMocks private CousineDomainDtoMapper cousineDomainDtoMapper = Mockito.spy(Mappers.getMapper(CousineDomainDtoMapper.class));;
    @InjectMocks private ProductDomainDtoMapper productDomainDtoMapper = Mockito.spy(Mappers.getMapper(ProductDomainDtoMapper.class));;

    @InjectMocks private OrderDomainDtoMapper orderDomainDtoMapper = Mockito.spy(Mappers.getMapper(OrderDomainDtoMapper.class));;;

    @Test
    public void fromOrder() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        OrderItem orderItem = order.getOrderItems().get(0);

        // when
        OrderResponse actual = orderDomainDtoMapper.mapToDto(order);

        // then
        assertThat(actual.getId()).isEqualTo(order.getId().getNumber());
        assertThat(actual.getDate()).isEqualTo(order.getCreatedAt());
        assertThat(actual.getCustomer()).isEqualTo(customerDomainDtoMapper.mapToDto(order.getCustomer()));
        assertThat(actual.getContact()).isEqualTo(order.getCustomer().getName());
        assertThat(actual.getStore()).isEqualTo(storeDomainDtoMapper.mapToDto(order.getStore()));
        assertThat(actual.getTotal()).isEqualTo(order.getTotal());
        assertThat(actual.getStatus().toString()).isEqualTo(order.getStatus().toString());
        assertThat(actual.getLastUpdate()).isEqualTo(order.getUpdatedAt());

        // and
        OrderItemResponse item = actual.getOrderItems().get(0);
        assertThat(item.getName()).isEqualTo(orderItem.getProduct().getName());
        assertThat(item.getPrice()).isEqualTo(orderItem.getProduct().getPrice());
        assertThat(item.getQuantity()).isEqualTo(orderItem.getQuantity());
        assertThat(item.getTotal()).isEqualTo(orderItem.getTotal());
    }
}