package com.delivery.database.repositories;

import static com.delivery.TestUtils.newInstanceWithProperties;
import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.database.entities.CustomerDb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DbCustomerRepositoryTest {

    @Autowired
    private DbCustomerRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @AutoConfigurationPackage
    @Configuration
    @EntityScan("com.delivery.database.entities")
    static class Config {
    }

    @Test
    public void existsByEmailReturnsTrue() throws Exception {
        // given
        CustomerDb customerData = newInstanceWithProperties(
                CustomerDb.class, TestCoreEntityGenerator.randomCustomer(), "id");

        // and
        entityManager.persistAndFlush(customerData);

        // when
        boolean actual = repository.existsByEmail(customerData.getEmail());

        // then
        assertThat(actual).isTrue();
    }

    @Test
    public void existsByEmailReturnsFalse() throws Exception {
        // given
        CustomerDb customerData = newInstanceWithProperties(
                CustomerDb.class, TestCoreEntityGenerator.randomCustomer(), "id");

        // and
        entityManager.persistAndFlush(customerData);

        // when
        boolean actual = repository.existsByEmail("not found" + customerData.getEmail());

        // then
        assertThat(actual).isFalse();
    }
}