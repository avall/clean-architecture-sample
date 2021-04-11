package com.delivery.core.usecases.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.repositories.IStoreRepository;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetStoreUseCaseTest {

    @InjectMocks
    private GetStoreUseCase useCase;

    @Mock
    private IStoreRepository repository;

    @Test
    public void getStoreByIdentityReturnsStore() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        GetStoreUseCase.InputValues input = GetStoreUseCase.InputValues.builder().id(store.getId()).build();

        // and
        doReturn(Optional.of(store))
                .when(repository)
                .getById(eq(store.getId()));

        // when
        Store actual = useCase.execute(input).getStore();

        // then
        assertThat(actual).isEqualTo(store);
    }

    @Test
    public void getStoreByIdentityThrowsNotFound() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        GetStoreUseCase.InputValues input = GetStoreUseCase.InputValues.builder().id(id).build();

        // and
        doReturn(Optional.empty())
                .when(repository)
                .getById(eq(id));

        // then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Store " + id.getNumber() + " not found");
    }
}