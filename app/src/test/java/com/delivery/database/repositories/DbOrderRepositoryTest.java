package com.delivery.database.repositories;

import static com.delivery.TestEntityGenerator.randomAddress;
import static com.delivery.TestEntityGenerator.randomDescription;
import static com.delivery.TestEntityGenerator.randomEmail;
import static com.delivery.TestEntityGenerator.randomName;
import static com.delivery.TestEntityGenerator.randomPassword;
import static com.delivery.TestEntityGenerator.randomPrice;
import static com.delivery.core.entities.TestCoreEntityGenerator.randomQuantity;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.database.entities.CousineDb;
import com.delivery.database.entities.CustomerDb;
import com.delivery.database.entities.OrderDb;
import com.delivery.database.entities.OrderItemDb;
import com.delivery.database.entities.ProductDb;
import com.delivery.database.entities.StoreDb;
import java.util.HashSet;
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
public class DbOrderRepositoryTest {

    @Configuration
    @EntityScan("com.delivery.database.entities")
    @AutoConfigurationPackage
    static class Config {
    }

    @Autowired
    private DbOrderRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void saveOrder() {
        // given
        CustomerDb customerData = new CustomerDb(randomName(), randomEmail(), randomAddress(), randomPassword());
        customerData = entityManager.persistFlushFind(customerData);

        //and
        CousineDb cousineData = CousineDb.newInstance(randomName());
        cousineData = entityManager.persistFlushFind(cousineData);

        // and
        StoreDb storeData = new StoreDb(randomName(), randomAddress(), cousineData, new HashSet<>());
        storeData = entityManager.persistFlushFind(storeData);

        // and
        ProductDb productData = new ProductDb(randomName(), randomDescription(), randomPrice(), storeData);
        productData = entityManager.persistAndFlush(productData);

        // and
        OrderItemDb orderItemData = OrderItemDb.newInstance(productData, randomQuantity());

        // and
        OrderDb toBeSaved = OrderDb.newInstance(
                customerData, storeData, new HashSet<>(singletonList(orderItemData))
        );

        // when
        OrderDb savedOrder = repository.save(toBeSaved);

        // then
        assertThat(savedOrder.getId()).isNotNull();
    }
}