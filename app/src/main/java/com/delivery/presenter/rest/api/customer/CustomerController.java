package com.delivery.presenter.rest.api.customer;

import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.customer.CreateCustomerUseCase;
import com.delivery.dto.AuthenticationResponse;
import com.delivery.dto.CustomerResponse;
import com.delivery.dto.SignInRequest;
import com.delivery.dto.SignUpRequest;
import com.delivery.presenter.mappers.domainDto.CustomerDomainDtoMapper;
import com.delivery.presenter.mappers.inputOutputDto.AuthenticateCustomerUseCaseInputMapper;
import com.delivery.presenter.mappers.inputOutputDto.AuthenticateCustomerUseCaseOutputMapper;
import com.delivery.presenter.mappers.inputOutputDto.CreateCustomerInputMapper;
import com.delivery.presenter.mappers.inputOutputDto.CreateCustomerUseCaseOutputMapper;
import com.delivery.presenter.usecases.security.AuthenticateCustomerUseCase;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class CustomerController implements CustomerResource {
    private final UseCaseExecutor useCaseExecutor;
    private final CreateCustomerUseCase createCustomerUseCase;
    private final CreateCustomerInputMapper createCustomerUseCaseInputMapper;
    private final CreateCustomerUseCaseOutputMapper createCustomerUseCaseOutputMapper;
    private final AuthenticateCustomerUseCase authenticateCustomerUseCase;
    private final AuthenticateCustomerUseCaseInputMapper authenticateCustomerUseCaseInputMapper;
    private final AuthenticateCustomerUseCaseOutputMapper authenticateCustomerUseCaseOutputMapper;
    private final CustomerDomainDtoMapper customerDomainDtoMapper;

    @Override
    public CompletableFuture<ResponseEntity<CustomerResponse>> signUp(@Valid @RequestBody SignUpRequest signUpRequest,
                                                                 HttpServletRequest httpServletRequest) {
        return useCaseExecutor.execute(
                createCustomerUseCase,
                createCustomerUseCaseInputMapper.map(signUpRequest),
                (outputValues) -> {
                    URI location = ServletUriComponentsBuilder
                        .fromContextPath(httpServletRequest)
                        .path("/Customer/{id}")
                        .buildAndExpand(outputValues.getCustomer().getId().getNumber())
                        .toUri();

                    return ResponseEntity.created(location).body(customerDomainDtoMapper.mapToDto(outputValues.getCustomer()));
                });
    }

    @Override
    public CompletableFuture<ResponseEntity<AuthenticationResponse>> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return useCaseExecutor.execute(
                authenticateCustomerUseCase,
                authenticateCustomerUseCaseInputMapper.map(signInRequest),
                (outputValues) -> ResponseEntity.ok(authenticateCustomerUseCaseOutputMapper.map(outputValues)));
    }
}
