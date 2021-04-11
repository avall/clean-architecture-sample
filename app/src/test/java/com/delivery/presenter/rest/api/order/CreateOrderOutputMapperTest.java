package com.delivery.presenter.rest.api.order;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.delivery.core.domain.Order;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.dto.ApiResponse;
import com.delivery.presenter.mappers.inputOutputDto.CreateOrderOutputMapper;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class CreateOrderOutputMapperTest {

    @Test
    public void mapOrderToResponseCreated() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        HttpServletRequest httpServletRequest = new MockHttpServletRequest("", "");

        URI location = ServletUriComponentsBuilder
            .fromContextPath(httpServletRequest)
            .path("/Order/{id}")
            .buildAndExpand(order.getId().getNumber())
            .toUri();

        // when
        ResponseEntity<ApiResponse> actual = ResponseEntity
            .created(location).body(Mappers.getMapper(CreateOrderOutputMapper.class).map(order));

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getBody().getSuccess()).isTrue();
        assertThat(actual.getBody().getMessage()).isEqualTo("order created successfully");
        assertThat(actual.getHeaders().getLocation().toString()).isEqualTo("http://localhost/Order/" + order.getId().getNumber());
    }
}