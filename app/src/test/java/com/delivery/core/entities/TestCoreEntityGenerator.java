package com.delivery.core.entities;

import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Customer;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Order;
import com.delivery.core.domain.OrderItem;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Status;
import com.delivery.core.domain.Store;
import com.github.javafaker.Faker;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class TestCoreEntityGenerator {
    private static final Faker faker = new Faker();

    public static Integer randomQuantity() {
        return randomNumberBetweenFiveAndTen();
    }

    public static Cousine randomCousine() {
        return Cousine.builder()
                .id(randomId())
                .name(faker.name().name())
                .build();
    }

    public static List<Cousine> randomCousines() {
        return randomItemsOf(TestCoreEntityGenerator::randomCousine);
    }

    public static Identity randomId() {
        return new Identity(faker.number().randomNumber());
    }

    public static Store randomStore() {
        return Store.builder()
                .id(randomId())
                .name(faker.name().name())
                .address(faker.address().streetAddress())
                .cousine(randomCousine())
                .build();
    }

    public static Product randomProduct() {
        return Product.builder()
                .id(randomId())
                .name(faker.name().name())
                .description(faker.name().fullName())
                .price(faker.number().randomDouble(2, 1, 50))
                .store(randomStore())
                .build();
    }

    private static int randomNumberBetweenFiveAndTen() {
        return faker.number().numberBetween(5, 10);
    }

    public static Customer randomCustomer() {
        return Customer.builder()
                .id(randomId())
                .name(faker.name().name())
                .email(faker.internet().emailAddress())
                .address(faker.address().fullAddress())
                .password(faker.lorem().fixedString(30))
                .build();
    }

    private static <T> List<T> randomItemsOf(Supplier<T> generator) {
        return IntStream.rangeClosed(0, randomNumberBetweenFiveAndTen())
                .mapToObj(number -> (T) generator.get())
                .collect(Collectors.toList());
    }

    public static Order randomOrder() {
        List<OrderItem> orderItems = Collections.singletonList(randomOrderItem());
        return Order.builder()
            .id(randomId())
            .customer(randomCustomer())
            .store(randomStore())
            .orderItems(orderItems)
            .status(Status.OPEN)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .total(Order.calculateTotal(orderItems))
            .build();
    }

    public static OrderItem randomOrderItem() {
        Product product = randomProduct();
        Integer quantity = randomQuantity();
        return OrderItem.builder()
            .id(randomId())
            .product(product)
            .quantity(quantity)
            .total(product.getPrice() * quantity)
            .build();
    }
}
