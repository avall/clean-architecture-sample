package com.delivery.presenter.usecases.security;

import static com.delivery.core.entities.TestCoreEntityGenerator.randomCustomer;
import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.core.mappers.CustomerDomainDbMapper;
import com.delivery.database.entities.CustomerDb;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

public class UserPrincipalTest {

    @Test
    public void fromCustomer() {
        // given
        CustomerDb customerData = Mappers.getMapper(CustomerDomainDbMapper.class).mapToDb(randomCustomer()) ;

        // when
        UserPrincipal actual = UserPrincipal.from(customerData);

        // then
        assertThat(actual).isEqualToComparingOnlyGivenFields(customerData, "name", "id", "password", "address");
        assertThat(actual.getAuthorities()).extracting("role").containsOnly("ROLE_USER");
    }
}