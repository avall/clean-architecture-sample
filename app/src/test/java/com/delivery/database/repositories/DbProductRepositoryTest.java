package com.delivery.database.repositories;

import static com.delivery.TestEntityGenerator.randomAddress;
import static com.delivery.TestEntityGenerator.randomName;
import static com.delivery.TestEntityGenerator.randomPrice;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.database.entities.CousineDb;
import com.delivery.database.entities.ProductDb;
import com.delivery.database.entities.StoreDb;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.Before;
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
public class DbProductRepositoryTest {

    @Autowired
    private DbProductRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @AutoConfigurationPackage
    @Configuration
    @EntityScan("com.delivery.database.entities")
    static class Config {
    }

    @Before
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void findByStoreIdAndIdIsInReturnListOfProductData() {
        // given
        CousineDb cousineData = entityManager.persistFlushFind(CousineDb.newInstance("name"));
        StoreDb storeData = entityManager.persistFlushFind(new StoreDb("name", randomAddress(), cousineData, new HashSet<>()));
        ProductDb productData = entityManager.persistAndFlush(new ProductDb("name", "description", randomPrice(), storeData));

        // when
        List<ProductDb> actual =
                repository.findByStoreIdAndIdIsIn(storeData.getId(), singletonList(productData.getId()));

        // then
        assertThat(actual).extracting("id").containsOnly(productData.getId());
    }

    @Test
    public void findByNameContainingIgnoreCase() {
        // given
        CousineDb cousineData = entityManager.persistFlushFind(CousineDb.newInstance(randomName()));
        StoreDb storeData = entityManager.persistFlushFind(new StoreDb(randomName(), randomAddress(), cousineData, new HashSet<>()));

        Arrays.stream(new String[]{"AABC", "ABBC", "ABCC"})
                .forEach(name -> {
                    String description = name;

                    if ("ABBC".equals(name)) {
                        description = "DESCRIPTION";
                    }

                    entityManager.persistAndFlush(new ProductDb(name, description, randomPrice(), storeData));
                });

        // when
        List<ProductDb> actual = repository.findByNameContainingOrDescriptionContainingAllIgnoreCase("abc", "des");

        // then
        assertThat(actual).hasSize(3).extracting("name").containsOnly("AABC", "ABBC", "ABCC");
    }
}