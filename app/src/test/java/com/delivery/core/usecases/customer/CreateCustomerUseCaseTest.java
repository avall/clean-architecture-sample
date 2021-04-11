package com.delivery.core.usecases.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import com.delivery.core.domain.Customer;
import com.delivery.core.domain.EmailAlreadyUsedException;
import com.delivery.core.domain.Identity;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.repositories.ICustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateCustomerUseCaseTest {

    @InjectMocks
    private CreateCustomerUseCase useCase;

    @Mock
    private ICustomerRepository repository;

    @Test
    public void executeThrowsExceptionWhenEmailIsAlreadyRegistered() {
        // given
        CreateCustomerUseCase.InputValues input =
            CreateCustomerUseCase.InputValues.builder()
            .name("name")
            .email("email@email.com").address("address")
            .password("password").build();

        // and
        doReturn(true)
                .when(repository)
                .existsByEmail(input.getEmail());

        // then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(EmailAlreadyUsedException.class)
                .hasMessage("Email address already in use!");
    }

    @Test
    public void executeReturnsCreatedCustomer() {
        // given
        Customer customer = TestCoreEntityGenerator.randomCustomer();
        Customer toBeMatched = Customer.builder()
                .id(Identity.nothing())
                .name(customer.getName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .password(customer.getPassword())
                .build();

        CreateCustomerUseCase.InputValues input = CreateCustomerUseCase.InputValues.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .password(customer.getPassword())
                .build();

        // and
        doReturn(customer)
                .when(repository)
                .persist(eq(toBeMatched));

        // when
        Customer actual = useCase.execute(input).getCustomer();

        // then
        assertThat(actual).isEqualTo(customer);
    }
}