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
public class GetAllCousinesUseCaseTest {

    @InjectMocks
    private GetAllCousinesUseCase useCase;

    @Mock
    private ICousineRepository repository;

    @Test
    public void returnsAllCousines() {
        // given
        List<Cousine> cousines = TestCoreEntityGenerator.randomCousines();
        GetAllCousinesUseCase.InputValues input =
                GetAllCousinesUseCase.InputValues.builder().build();

        // and
        doReturn(cousines)
                .when(repository)
                .getAll();

        // when
        final List<Cousine> actual = useCase.execute(input).getCousines();

        // then
        assertThat(actual).isEqualTo(cousines);
    }
}