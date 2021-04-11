package com.delivery.presenter.rest.api.order;

import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.order.CreateOrderUseCase;
import com.delivery.core.usecases.order.DeleteOrderUseCase;
import com.delivery.core.usecases.order.DeliveryOrderUseCase;
import com.delivery.core.usecases.order.GetCustomerOrderUseCase;
import com.delivery.core.usecases.order.GetOrderUseCase;
import com.delivery.core.usecases.order.PayOrderUseCase;
import com.delivery.dto.ApiResponse;
import com.delivery.dto.CustomerResponse;
import com.delivery.dto.OrderRequest;
import com.delivery.dto.OrderResponse;
import com.delivery.presenter.mappers.domainDto.CustomerDomainDtoMapper;
import com.delivery.presenter.mappers.domainDto.OrderDomainDtoMapper;
import com.delivery.presenter.mappers.inputOutputDto.CreateOrderInputMapper;
import com.delivery.presenter.mappers.inputOutputDto.CreateOrderOutputMapper;
import com.delivery.presenter.usecases.security.CurrentUser;
import com.delivery.presenter.usecases.security.UserPrincipal;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OrderController implements OrderResource {
    private final UseCaseExecutor useCaseExecutor;
    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final GetCustomerOrderUseCase getCustomerOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;
    private final PayOrderUseCase payOrderUseCase;
    private final DeliveryOrderUseCase deliveryOrderUseCase;
    private final CustomerDomainDtoMapper customerDomainDtoMapper;
    private final OrderDomainDtoMapper orderDomainDtoMapper;
    private final CreateOrderInputMapper createOrderInputMapper;
    private final CreateOrderOutputMapper createOrderOutputMapper;

    @Override
    public CompletableFuture<ResponseEntity<OrderResponse>> create(@CurrentUser UserPrincipal userDetails,
                                                                 HttpServletRequest httpServletRequest,
                                                                 @Valid @RequestBody OrderRequest orderRequest) {
        return useCaseExecutor.execute(
                createOrderUseCase,
                createOrderInputMapper.map(orderRequest, userDetails),
                (outputValues) -> {
                    URI location = ServletUriComponentsBuilder
                        .fromContextPath(httpServletRequest)
                        .path("/Order/{id}")
                        .buildAndExpand(outputValues.getOrder().getId().getNumber())
                        .toUri();

                    return ResponseEntity.created(location).body(orderDomainDtoMapper.mapToDto(outputValues.getOrder()));
                });
    }

    @Override
    public CompletableFuture<OrderResponse> getById(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getOrderUseCase,
                GetOrderUseCase.InputValues.builder().id(new Identity(id)).build(),
                (outputValues) -> orderDomainDtoMapper.mapToDto(outputValues.getOrder())
        );
    }

    @Override
    public CompletableFuture<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getCustomerOrderUseCase,
                GetCustomerOrderUseCase.InputValues.builder().id(new Identity(id)).build(),
                (outputValues) -> customerDomainDtoMapper.mapToDto(outputValues.getCustomer())
        );
    }

    @Override
    public CompletableFuture<ApiResponse> delete(@PathVariable Long id) {
        return useCaseExecutor.execute(
                deleteOrderUseCase,
                DeleteOrderUseCase.InputValues.builder().id(new Identity(id)).build(),
                (outputValues) -> new ApiResponse(true, "Order successfully canceled")
        );
    }

    @Override
    public CompletableFuture<ApiResponse> pay(@PathVariable Long id) {
        return useCaseExecutor.execute(
                payOrderUseCase,
                DeleteOrderUseCase.InputValues.builder().id(new Identity(id)).build(),
                (outputValues) -> new ApiResponse(true, "Order successfully paid")
        );
    }

    @Override
    public CompletableFuture<ApiResponse> delivery(@PathVariable Long id) {
        return useCaseExecutor.execute(
                deliveryOrderUseCase,
                DeleteOrderUseCase.InputValues.builder().id(new Identity(id)).build(),
                (outputValues) -> new ApiResponse(true, "Order successfully delivered")
        );
    }
}
