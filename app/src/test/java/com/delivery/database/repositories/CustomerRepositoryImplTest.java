package com.delivery.database.repositories;

import static com.delivery.TestUtils.newInstanceWithProperties;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import com.delivery.core.domain.Customer;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.mappers.CustomerDomainDbMapper;
import com.delivery.database.repositories.impl.CustomerRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CustomerRepositoryImplTest {

    @InjectMocks private CustomerRepositoryImpl customerRepository;
    @Mock private DbCustomerRepository dbCustomerRepository;
    @InjectMocks private CustomerDomainDbMapper customerDomainDbMapper = Mockito.spy(Mappers.getMapper(CustomerDomainDbMapper.class));

    @Test
    public void saveShouldPersistCustomerDataAndReturnsCustomer() throws Exception {
        // given
        Customer expected = TestCoreEntityGenerator.randomCustomer();
        Customer input = newInstanceWithProperties(Customer.class, expected, "id");

        // and
        Mockito.doReturn(Mappers.getMapper(CustomerDomainDbMapper.class).mapToDb(expected))
                .when(dbCustomerRepository)
                .save(eq(Mappers.getMapper(CustomerDomainDbMapper.class).mapToDb(input)));

        // when
        Customer actual = customerRepository.persist(input);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void existsByEmailReturnsFalse() {
        //given
        String email = "email";

        // and
        doReturn(false)
                .when(dbCustomerRepository)
                .existsByEmail(eq(email));

        // when
        boolean actual = customerRepository.existsByEmail(email);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    public void existsByEmailReturnsTrue() {
        //given
        String email = "email";

        // and
        doReturn(true)
                .when(dbCustomerRepository)
                .existsByEmail(eq(email));

        // when
        boolean actual = customerRepository.existsByEmail(email);

        // then
        assertThat(actual).isTrue();
    }
}