package com.delivery.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Value;

@Value
public class SignUpRequest {
    @NotBlank
    @Size(min = 4, max = 50)
    private final String name;

    @Email
    @NotBlank
    @Size(max = 100)
    private final String email;

    @NotBlank
    private final String address;

    @NotBlank
    @Size(min = 6, max = 50)
    private final String password;
}
