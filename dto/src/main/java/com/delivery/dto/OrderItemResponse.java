package com.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private String name;
    private Double price;
    private Integer quantity;
    private Double total;
}
