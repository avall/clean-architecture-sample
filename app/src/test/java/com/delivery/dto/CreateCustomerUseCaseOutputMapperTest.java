package com.delivery.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.core.domain.Customer;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.presenter.mappers.inputOutputDto.CreateCustomerUseCaseOutputMapper;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class CreateCustomerUseCaseOutputMapperTest {

    @Test
    public void mapReturnsResponseEntityWithCustomerPath() {
        // given
        Customer customer = TestCoreEntityGenerator.randomCustomer();
        HttpServletRequest httpServletRequest = new MockHttpServletRequest("", "");

        URI location = ServletUriComponentsBuilder
            .fromContextPath(httpServletRequest)
            .path("/Customer/{id}")
            .buildAndExpand(customer.getId().getNumber())
            .toUri();

        // given
        ResponseEntity<ApiResponse> actual = ResponseEntity
            .created(location)
            .body(Mappers.getMapper(CreateCustomerUseCaseOutputMapper.class).map(customer));

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getBody().getSuccess()).isTrue();
        assertThat(actual.getBody().getMessage()).isEqualTo("registered successfully");
        assertThat(actual.getHeaders().getLocation().toString()).isEqualTo("http://localhost/Customer/" + customer.getId().getNumber());
    }
}