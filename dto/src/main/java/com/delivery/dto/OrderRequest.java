package com.delivery.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class OrderRequest {
    @NotNull
    private final Long storeId;

    @NotEmpty
    private final List<OrderRequestItem> orderItems;
}
