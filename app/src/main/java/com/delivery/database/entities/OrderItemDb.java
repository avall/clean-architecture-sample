package com.delivery.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Entity(name = "orderItem")
@EqualsAndHashCode(callSuper = true, of = {"order", "product", "price", "quantity", "total"})
@Getter
@NoArgsConstructor
@Setter
@Table(name = "order_item")
@ToString(of = {"order", "product", "price", "quantity", "total"})
@SuperBuilder
public class OrderItemDb extends BaseDbEntity {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderDb order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductDb product;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double total;

    // TODO: test
    public static OrderItemDb newInstance(ProductDb productData, Integer quantity) {
        return OrderItemDb.builder()
                .id(null)
                .order(null)
                .product(productData)
                .price(productData.getPrice())
                .quantity(quantity)
                .total(quantity * productData.getPrice())
                .build();
    }
}
