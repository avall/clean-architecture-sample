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
public class StoreResponse {
    private Long id;
    private String name;
    private String address;
    private Long cousineId;
}
