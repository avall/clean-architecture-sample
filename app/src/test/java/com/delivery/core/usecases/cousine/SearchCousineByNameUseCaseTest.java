package com.delivery.core.usecases.cousine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.delivery.core.domain.Cousine;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.repositories.ICousineRepository;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SearchCousineByNameUseCaseTest {

    @Mock
    private ICousineRepository repository;

    @InjectMocks
    private SearchCousineByNameUseCase useCase;

    @Test
    public void searchCousineByName() {
        // given
        List<Cousine> cousines = TestCoreEntityGenerator.randomCousines();
        String searchText = "abc";
        SearchCousineByNameUseCase.InputValues input =
                SearchCousineByNameUseCase.InputValues.builder().searchText(searchText).build();

        // and
        doReturn(cousines)
                .when(repository)
                .searchByName(searchText);

        // when
        final List<Cousine> actual = useCase.execute(input).getCousines();

        // then
        assertThat(actual).isEqualTo(cousines);
    }
}