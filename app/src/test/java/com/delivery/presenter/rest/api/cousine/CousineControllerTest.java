package com.delivery.presenter.rest.api.cousine;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.usecases.cousine.GetAllCousinesUseCase;
import com.delivery.core.usecases.cousine.GetStoresByCousineUseCase;
import com.delivery.core.usecases.cousine.SearchCousineByNameUseCase;
import com.delivery.presenter.rest.api.common.BaseControllerTest;
import com.delivery.presenter.usecases.UseCaseExecutorImpl;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CousineController.class, secure = false)
public class CousineControllerTest extends BaseControllerTest {

    @Configuration
    @ComponentScan(basePackages = {"com.delivery.presenter.mappers"
        , "com.delivery.presenter.rest.api.cousine"
        , "com.delivery.presenter.rest.api.common"})
    static class Config {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @SpyBean
    private UseCaseExecutorImpl useCaseExecutor;

    @MockBean
    private GetStoresByCousineUseCase getStoresByCousineUseCase;

    @MockBean
    private GetAllCousinesUseCase getAllCousinesUseCase;

    @MockBean
    private SearchCousineByNameUseCase searchCousineByNameUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Override
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    @Test
    public void getStoreByCousineIdReturnsNotFound() throws Exception {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        GetStoresByCousineUseCase.InputValues input = GetStoresByCousineUseCase.InputValues.builder().id(id).build();

        // and
        doThrow(new NotFoundException("Error"))
                .when(getStoresByCousineUseCase)
                .execute(eq(input));

        // when
        final RequestBuilder payload = asyncGetRequest("/Cousine/" + id.getNumber() + "/stores");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Error")));
    }

    @Test
    public void getStoresByCousineIdReturnsOk() throws Exception {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        Identity id = store.getCousine().getId();
        GetStoresByCousineUseCase.InputValues input = GetStoresByCousineUseCase.InputValues.builder().id(id).build();
        GetStoresByCousineUseCase.OutputValues output =
            GetStoresByCousineUseCase.OutputValues.builder().stores(singletonList(store)).build();

        // and
        doReturn(output)
                .when(getStoresByCousineUseCase)
                .execute(eq(input));

        // when
        final RequestBuilder payload = asyncGetRequest("/Cousine/" + id.getNumber() + "/stores");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(store.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(store.getName())))
                .andExpect(jsonPath("$[0].address", is(store.getAddress())))
                .andExpect(jsonPath("$[0].cousineId", is(id.getNumber().intValue())));
    }

    @Test
    public void getAllCousinesReturnsOk() throws Exception {
        // given
        List<Cousine> cousines = TestCoreEntityGenerator.randomCousines();
        Cousine firstCousine = cousines.get(0);

        GetAllCousinesUseCase.InputValues input = GetAllCousinesUseCase.InputValues.builder()
            .build();
        GetAllCousinesUseCase.OutputValues output = GetAllCousinesUseCase.OutputValues.builder().cousines(cousines).build();

        // and
        doReturn(output)
                .when(getAllCousinesUseCase)
                .execute(input);

        // when
        final RequestBuilder payload = asyncGetRequest("/Cousine");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(cousines.size())))
                .andExpect(jsonPath("$[0].id", is(firstCousine.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(firstCousine.getName())));
    }

    @Test
    public void searchCousineByNameReturnsOk() throws Exception {
        // given
        List<Cousine> cousines = TestCoreEntityGenerator.randomCousines();
        Cousine firstCousine = cousines.get(0);
        SearchCousineByNameUseCase.InputValues input = SearchCousineByNameUseCase.InputValues.builder().searchText("abc").build();
        SearchCousineByNameUseCase.OutputValues output = SearchCousineByNameUseCase.OutputValues.builder().cousines(cousines).build();

        // and
        doReturn(output)
                .when(searchCousineByNameUseCase)
                .execute(input);
        // when
        final RequestBuilder payload = asyncGetRequest("/Cousine/search/abc");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(cousines.size())))
                .andExpect(jsonPath("$[0].id", is(firstCousine.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(firstCousine.getName())));
    }
}