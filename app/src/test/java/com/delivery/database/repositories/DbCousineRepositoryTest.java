package com.delivery.database.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.delivery.database.entities.CousineDb;
import java.util.Arrays;
import java.util.List;
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
public class DbCousineRepositoryTest {

    @Autowired
    private DbCousineRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Configuration
    @AutoConfigurationPackage
    @EntityScan("com.delivery.database.entities")
    static class Config {
    }

    @Test
    public void findByNameIgnoreCase() {
        // given
        Arrays.stream(new String[]{"aAbc", "abBc", "abCc"})
                .forEach(name -> entityManager.persistAndFlush(CousineDb.newInstance(name)));

        // when
        final List<CousineDb> actual = repository.findByNameContainingIgnoreCase("abc");

        // then
        assertThat(actual)
                .extracting("name")
                .containsOnly("aAbc", "abCc");
    }


}