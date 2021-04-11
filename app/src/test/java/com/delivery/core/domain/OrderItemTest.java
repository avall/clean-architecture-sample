package com.delivery.core.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.core.entities.TestCoreEntityGenerator;
import org.junit.Test;

public class OrderItemTest {

    @Test
    public void newInstance() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        Product product = TestCoreEntityGenerator.randomProduct();
        Integer quantity = TestCoreEntityGenerator.randomQuantity();

        // when
        OrderItem actual = OrderItem.builder()
        .id(id)
        .product(product)
        .quantity(quantity)
        .total(quantity * product.getPrice())
        .build();

        // then
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getProduct()).isEqualTo(product);
        assertThat(actual.getTotal()).isEqualTo(product.getPrice() * quantity);
    }
}