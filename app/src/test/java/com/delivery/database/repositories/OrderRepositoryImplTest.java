package com.delivery.database.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import com.delivery.TestUtils;
import com.delivery.core.domain.Order;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.mappers.CousineDomainDbMapper;
import com.delivery.core.mappers.CustomerDomainDbMapper;
import com.delivery.core.mappers.OrderDomainDbMapper;
import com.delivery.core.mappers.OrderItemDomainDbMapper;
import com.delivery.core.mappers.ProductDomainDbMapper;
import com.delivery.core.mappers.StoreDomainDbMapper;
import com.delivery.database.entities.OrderDb;
import com.delivery.database.repositories.impl.OrderRepositoryImpl;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderRepositoryImplTest {

    @InjectMocks private OrderRepositoryImpl orderRepository;
    @InjectMocks private OrderItemDomainDbMapper orderItemDomainDbMapper  = Mockito.spy(Mappers.getMapper(OrderItemDomainDbMapper.class));
    @InjectMocks private CustomerDomainDbMapper customerDomainDbMapper  = Mockito.spy(Mappers.getMapper(CustomerDomainDbMapper.class));
    @InjectMocks private StoreDomainDbMapper storeDomainDbMapper = Mockito.spy(Mappers.getMapper(StoreDomainDbMapper.class));
    @InjectMocks private OrderDomainDbMapper orderDomainDbMapper = Mockito.spy(Mappers.getMapper(OrderDomainDbMapper.class));;
    @InjectMocks private CousineDomainDbMapper cousineDomainDbMapper = Mockito.spy(Mappers.getMapper(CousineDomainDbMapper.class));;
    @InjectMocks private ProductDomainDbMapper productDomainDbMapper = Mockito.spy(Mappers.getMapper(ProductDomainDbMapper.class));;

    @Mock private DbOrderRepository jpaRepository;

    @Test
    public void getByIdReturnOrderData() {
        // given
        Order expected = TestCoreEntityGenerator.randomOrder();
        OrderDb toBeReturned = orderDomainDbMapper.mapToDb(expected);

        // and
        doReturn(Optional.of(toBeReturned))
                .when(jpaRepository)
                .findById(eq(expected.getId().getNumber()));

        // when
        Optional<Order> actual = orderRepository.getById(expected.getId());

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }

    @Test
    public void persistSaveAndReturnOrderData() throws Exception {
        // given
        Order expected = TestCoreEntityGenerator.randomOrder();
        OrderDb toBeReturned = orderDomainDbMapper.mapToDb(expected);
        OrderDb toBeMatched = TestUtils.newInstanceWithProperties(OrderDb.class, toBeReturned);

        // and
        doReturn(toBeReturned)
                .when(jpaRepository)
                .save(eq(toBeMatched));

        // when
        Order actual = orderRepository.persist(expected);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}