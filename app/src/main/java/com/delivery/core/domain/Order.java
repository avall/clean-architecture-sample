package com.delivery.core.domain;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {"status", "customer", "store", "total", "createdAt"})
public class Order extends BaseDomainEntity {
    private Status status;
    private Customer customer;
    private Store store;
    private List<OrderItem> orderItems;
    private Double total;
    private Instant createdAt;
    private Instant updatedAt;

    public static Double calculateTotal(List<OrderItem> orderItems) {
        return orderItems
                .stream()
                .mapToDouble(OrderItem::getTotal)
                .sum();
    }

    public Order delete() {
        if (this.status != Status.OPEN) {
            throw new IllegalStateException("Order should be open to be cancelled");
        }

        return newInstanceWith(Status.CANCELLED);
    }

    public Order delivery() {
        if (this.status != Status.PAID) {
            throw new IllegalStateException("Order should be paid to be delivered");
        }

        return newInstanceWith(Status.DELIVERED);
    }

    public Order pay() {
        if (this.status != Status.OPEN) {
            throw new IllegalStateException("Order should be open to be paid");
        }

        return newInstanceWith(Status.PAID);
    }

    private Order newInstanceWith(Status status) {
        return Order.builder()
                .id(getId())
                .status(status)
                .customer(customer)
                .store(store)
                .orderItems(orderItems)
                .total(total)
                .createdAt(createdAt)
                .updatedAt(Instant.now())
                .build();
    }
}
