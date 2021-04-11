package com.delivery.presenter.rest.api.customer;

import com.delivery.dto.AuthenticationResponse;
import com.delivery.dto.CustomerResponse;
import com.delivery.dto.SignInRequest;
import com.delivery.dto.SignUpRequest;
import java.util.concurrent.CompletableFuture;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Customer")
public interface CustomerResource {

    @PostMapping
    CompletableFuture<ResponseEntity<CustomerResponse>> signUp(
            @Valid @RequestBody SignUpRequest request, HttpServletRequest httpServletRequest);

    @PostMapping("/auth")
    CompletableFuture<ResponseEntity<AuthenticationResponse>> signIn(@Valid @RequestBody SignInRequest request);
}
