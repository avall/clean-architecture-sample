package com.delivery.database.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.delivery.core.domain.Cousine;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.mappers.CousineDomainDbMapper;
import com.delivery.database.entities.CousineDb;
import com.delivery.database.repositories.impl.CousineRepositoryImpl;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CousineRepositoryImplTest {

    @Mock private DbCousineRepository dbCousineRepository;
    @InjectMocks private CousineRepositoryImpl cousineRepository;
    @InjectMocks private CousineDomainDbMapper cousineDomainDbMapper = Mockito
        .spy(Mappers.getMapper(CousineDomainDbMapper.class));;

    @Test
    public void getAllReturnsAllCousines() {
        // given
        Cousine cousine = TestCoreEntityGenerator.randomCousine();
        CousineDb cousineData = Mappers.getMapper(CousineDomainDbMapper.class).mapToDb(cousine);

        // and
        doReturn(Collections.singletonList(cousineData))
                .when(dbCousineRepository)
                .findAll();
        // when
        final List<Cousine> actual = cousineRepository.getAll();

        // then
        assertThat(actual).containsOnly(cousine);
    }

    @Test
    public void searchCousineByNameReturnsAllCousines() {
        // given
        Cousine cousine = TestCoreEntityGenerator.randomCousine();
        CousineDb cousineData = Mappers.getMapper(CousineDomainDbMapper.class).mapToDb(cousine);
        String search = "abc";

        // and
        doReturn(Collections.singletonList(cousineData))
                .when(dbCousineRepository)
                .findByNameContainingIgnoreCase(search);

        // when
        final List<Cousine> actual = cousineRepository.searchByName(search);

        // then
        assertThat(actual).isEqualTo(Collections.singletonList(cousine));
    }
}