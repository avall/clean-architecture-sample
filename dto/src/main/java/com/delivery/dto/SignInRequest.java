package com.delivery.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SignInRequest {

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    @Size(min = 6, max = 50)
    private final String password;
}
