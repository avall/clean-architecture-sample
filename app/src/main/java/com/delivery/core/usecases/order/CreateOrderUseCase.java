package com.delivery.core.usecases.order;

import com.delivery.core.domain.Customer;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Order;
import com.delivery.core.domain.OrderItem;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Status;
import com.delivery.core.domain.Store;
import com.delivery.core.repositories.IOrderRepository;
import com.delivery.core.usecases.UseCase;
import com.delivery.core.usecases.product.GetProductsByStoreAndProductsIdUseCase;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOrderUseCase implements UseCase<CreateOrderUseCase.InputValues, CreateOrderUseCase.OutputValues> {
    private final GetProductsByStoreAndProductsIdUseCase getProductsByStoreAndProductsIdUseCase;
    private final IOrderRepository orderRepository;

    @Override
    public OutputValues execute(InputValues input) {
        Order order = createOrder(input);

        return OutputValues.builder().order(orderRepository.persist(order)).build();
    }

    private Order createOrder(InputValues input) {
        final List<OrderItem> orderItems = createOrderItems(input);

        return Order.builder()
                .id(Identity.nothing())
                .customer(input.getCustomer())
                .store(getFirstProductStore(orderItems))
                .orderItems(orderItems)
                .status(Status.OPEN)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .total(Order.calculateTotal(orderItems))
                .build();
    }

    private Store getFirstProductStore(List<OrderItem> orderItems) {
        return orderItems.get(0).getProduct().getStore();
    }

    private List<OrderItem> createOrderItems(InputValues input) {
        Map<Identity, Product> productMap = getProducts(input);

        return input
                .getOrderItems()
                .stream()
                .map(inputItem -> createOrderItem(inputItem, productMap))
                .collect(Collectors.toList());
    }

    private OrderItem createOrderItem(InputItem inputItem, Map<Identity, Product> productMap) {
        Product product = productMap.get(inputItem.getId());

        return OrderItem.builder()
            .id(Identity.nothing())
            .product(product)
            .quantity(inputItem.getQuantity())
            .total(inputItem.getQuantity() * product.getPrice())
            .build();
    }

    private Map<Identity, Product> getProducts(InputValues input) {
        GetProductsByStoreAndProductsIdUseCase.InputValues inputValues =
                GetProductsByStoreAndProductsIdUseCase.InputValues.builder()
                    .storeId(input.getStoreId())
                .productsId(createListOfProductsId(input.getOrderItems())).build();

        return getProductsByStoreAndProductsIdUseCase.execute(inputValues)
                .getProducts()
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
    }

    private List<Identity> createListOfProductsId(List<InputItem> inputItems) {
        return inputItems
                .stream()
                .map(InputItem::getId)
                .collect(Collectors.toList());
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class InputValues implements UseCase.InputValues {
        private Customer customer;
        private Identity storeId;
        private List<InputItem> orderItems;
    }

    @Value
    @Builder
    public static class OutputValues implements UseCase.OutputValues {
        private final Order order;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class InputItem {
        private Identity id;
        private int quantity;
    }
}
