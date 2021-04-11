package com.delivery.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.core.domain.Customer;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.presenter.mappers.domainDto.CustomerDomainDtoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CustomerResponseTest {
    @InjectMocks private CustomerDomainDtoMapper customerDomainDbMapper  = Mockito.spy(Mappers.getMapper(CustomerDomainDtoMapper.class));

    @Test
    public void fromCustomer() {
        // given
        Customer customer = TestCoreEntityGenerator.randomCustomer();

        // when
        CustomerResponse actual = customerDomainDbMapper.mapToDto(customer);

        // then
        assertThat(actual.getName()).isEqualTo(customer.getName());
        assertThat(actual.getEmail()).isEqualTo(customer.getEmail());
        assertThat(actual.getAddress()).isEqualTo(customer.getAddress());
    }
}