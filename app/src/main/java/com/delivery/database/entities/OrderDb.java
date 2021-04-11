package com.delivery.database.entities;

import com.delivery.core.domain.Status;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity(name = "order")
@EqualsAndHashCode(callSuper = true, of = {"customer", "store", "total", "status", "createdAt", "updatedAt"})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "orders")
@ToString(of = {"customer", "store", "total", "status", "createdAt", "updatedAt"})
@SuperBuilder
public class OrderDb extends BaseDbEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerDb customer;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreDb store;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "order",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    @Default
    private Set<OrderItemDb> orderItems = new HashSet<>();

    @Column(nullable = false)
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // TODO: test
    private void addOrderItem(OrderItemDb orderItem) {
        if (this.orderItems == null) {
            this.orderItems = new HashSet<>();
        }

        orderItem.setOrder(this);
        this.orderItems.add(orderItem);

        this.calculateTotal();
    }

    // TODO: test
    public static OrderDb newInstance(CustomerDb customer,
                                        StoreDb store,
                                        Set<OrderItemDb> orderItems) {
        OrderDb order = OrderDb.builder()
                .id(null)
                .customer(customer)
                .store(store)
                .orderItems(null)
                .total(0d)
                .status(Status.OPEN)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        orderItems.forEach(order::addOrderItem);

        return order;
    }

    private void calculateTotal() {
        this.total = this.orderItems
                .stream()
                .mapToDouble(OrderItemDb::getTotal)
                .sum();
    }
}
