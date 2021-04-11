package com.delivery.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class OrderRequestItem {
    @NotNull
    private final Long id;

    @Min(1)
    @NotNull
    private final Integer quantity;
}
