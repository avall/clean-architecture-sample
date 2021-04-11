package com.delivery.core.usecases.cousine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.repositories.IStoreRepository;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetStoresByCousineUseCaseTest {

    @InjectMocks private GetStoresByCousineUseCase useCase;
    @Mock private IStoreRepository storeRepository;

    @Test
    public void returnsStoresWhenCousineIdIsFound() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        Identity id = TestCoreEntityGenerator.randomId();
        GetStoresByCousineUseCase.InputValues input =
            GetStoresByCousineUseCase.InputValues.builder().id(id).build();

        // and
        doReturn(Collections.singletonList(store))
                .when(storeRepository)
                .getStoresById(id);

        // when
        final List<Store> actual = useCase.execute(input).getStores();

        // then
        assertThat(actual).containsOnly(store);
    }

    @Test
    public void throwsExceptionWhenCousineIdIsNotFound() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        GetStoresByCousineUseCase.InputValues input = GetStoresByCousineUseCase
            .InputValues.builder().id(id).build();

        // and
        doReturn(Collections.emptyList())
                .when(storeRepository)
                .getStoresById(id);

        // then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Cousine " + id.getNumber() + " not found");
    }
}