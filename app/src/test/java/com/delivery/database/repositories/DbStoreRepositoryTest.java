package com.delivery.database.repositories;

import static com.delivery.TestEntityGenerator.randomAddress;
import static com.delivery.TestEntityGenerator.randomPrice;
import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.database.entities.CousineDb;
import com.delivery.database.entities.ProductDb;
import com.delivery.database.entities.StoreDb;
import java.util.ArrayList;
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
public class DbStoreRepositoryTest {

    @Autowired
    private DbStoreRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Configuration
    @AutoConfigurationPackage
    @EntityScan("com.delivery.database.entities")
    static class Config {
    }

    private CousineDb cousineData;

    @Before
    public void setUp() throws Exception {
        cousineData = entityManager.persistFlushFind(CousineDb.newInstance("name"));
    }

    @Test
    public void findByNameOrDescriptionContainingIgnoreCaseAllReturnsAllMatchStores() {
        // given
        Arrays.stream(new String[]{"aAbc", "abBc", "abCc"})
                .forEach(name -> {
                    final StoreDb storeData = new StoreDb(name, randomAddress(), cousineData, new HashSet<>());
                    entityManager.persistAndFlush(storeData);
                });

        // when
        List<StoreDb> actual = repository.findByNameContainingIgnoreCase("abc");

        // then
        assertThat(actual).hasSize(2).extracting("name").containsOnly("aAbc", "abCc");
    }

    @Test
    public void findProductsByIdReturnsAllProducts() {
        // given
        StoreDb storeData = entityManager.persistFlushFind(new StoreDb("name", randomAddress(), cousineData, new HashSet<>()));

        // and
        Arrays.stream(new String[]{"product A", "product B"})
                .forEach(name -> {
                    final ProductDb productData = new ProductDb(name, "desc", randomPrice(), storeData);
                    entityManager.persistAndFlush(productData);
                });

        // when
        List<ProductDb> actual = repository.findProductsById(storeData.getId());

        // then
        assertThat(actual).hasSize(2).extracting("name").containsOnly("product A", "product B");
    }

    @Test
    public void getStoresByCousineId() {
        // given
        CousineDb cousine = entityManager.persistFlushFind(CousineDb.newInstance("name1"));
        StoreDb storeA = entityManager.persistFlushFind(new StoreDb( "name A", randomAddress(), cousine, new HashSet<>()));
        StoreDb storeB = entityManager.persistFlushFind(new StoreDb( "name B", randomAddress(), cousine, new HashSet<>()));

        // and
        cousine.addStore(storeA);
        cousine.addStore(storeB);
        entityManager.persistAndFlush(cousine);

        // when
        List<StoreDb> actual = new ArrayList<>(repository.findStoresById(cousine.getId()));

        // then
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0)).isEqualToComparingOnlyGivenFields(storeA, "id", "name", "address");
        assertThat(actual.get(1)).isEqualToComparingOnlyGivenFields(storeB, "id", "name", "address");
    }

    @Test
    public void getStoresByCousineIdReturnsEmpty() {
        List<StoreDb> actual = repository.findStoresById(0L);

        assertThat(actual).hasSize(0);
    }
}