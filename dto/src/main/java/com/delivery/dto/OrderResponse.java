package com.delivery.dto;

import java.time.Instant;
import java.util.List;
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
public class OrderResponse {
    private Long id;
    private Instant date;
    private CustomerResponse customer;
    private StoreResponse store;
    private String contact;
    private Double total;
    private Status status;
    private Instant lastUpdate;
    private List<OrderItemResponse> orderItems;

}
