package com.delivery.presenter.rest.api.customer;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.delivery.core.domain.Customer;
import com.delivery.core.domain.EmailAlreadyUsedException;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.usecases.customer.CreateCustomerUseCase;
import com.delivery.dto.ApiResponse;
import com.delivery.dto.AuthenticationResponse;
import com.delivery.dto.SignInRequest;
import com.delivery.dto.SignUpRequest;
import com.delivery.presenter.mappers.inputOutputDto.AuthenticateCustomerUseCaseInputMapper;
import com.delivery.presenter.mappers.inputOutputDto.AuthenticateCustomerUseCaseOutputMapper;
import com.delivery.presenter.mappers.inputOutputDto.CreateCustomerInputMapper;
import com.delivery.presenter.mappers.inputOutputDto.CreateCustomerUseCaseOutputMapper;
import com.delivery.presenter.rest.api.common.BaseControllerTest;
import com.delivery.presenter.usecases.UseCaseExecutorImpl;
import com.delivery.presenter.usecases.security.AuthenticateCustomerUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class, secure = false)
public class CustomerControllerTest extends BaseControllerTest {

    @Configuration
    @ComponentScan(basePackages = {
        "com.delivery.presenter.mappers",
        "com.delivery.presenter.rest.api.customer",
        "com.delivery.presenter.rest.api.common"
    })
    static class Config {
    }

    private JacksonTester<SignUpRequest> signUpJson;
    private JacksonTester<SignInRequest> signInJson;

    @MockBean
    private AuthenticateCustomerUseCase authenticateCustomerUseCase;

    @MockBean
    private CreateCustomerUseCase createCustomerUseCase;

    @MockBean
    private CreateCustomerInputMapper createCustomerInputMapper;

    @MockBean
    private AuthenticateCustomerUseCaseInputMapper authenticateCustomerUseCaseInputMapper;

    @MockBean
    private AuthenticateCustomerUseCaseOutputMapper authenticateCustomerUseCaseOutputMapper;

    @MockBean
    private CreateCustomerUseCaseOutputMapper CreateCustomerUseCaseOutputMapper;

    @SpyBean
    private UseCaseExecutorImpl useCaseExecutor;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Override
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    @Test
    public void signInReturnsOkWhenAuthenticationWorks() throws Exception {
        // given
        SignInRequest signInRequest =
            SignInRequest.builder().email("email@email.com").password("password").build();
        AuthenticateCustomerUseCase.InputValues input = Mappers
            .getMapper(AuthenticateCustomerUseCaseInputMapper.class)
            .map(signInRequest);

        String token = "token";
        AuthenticateCustomerUseCase.OutputValues output = AuthenticateCustomerUseCase.OutputValues
            .builder().jwtToken(token).build();

        String payload = signInJson.write(signInRequest).getJson();

        // and
        doReturn(input)
            .when(authenticateCustomerUseCaseInputMapper)
            .map(eq(signInRequest));

        doReturn(AuthenticationResponse.builder().success(true).token(token).build())
            .when(authenticateCustomerUseCaseOutputMapper)
            .map(eq(output));

        // and
        doReturn(output)
                .when(authenticateCustomerUseCase)
                .execute(eq(input));

        // when
        RequestBuilder request = asyncPostRequest("/Customer/auth", payload);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.token", is(token)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        ;
    }

    @Test
    public void signInReturnsBadRequestWhenAuthenticationManagerFails() throws Exception {
        // given
        SignInRequest signInRequest =
            SignInRequest.builder().email("email@email.com")
            .password("password").build();
        String payload = signInJson.write(signInRequest).getJson();
        AuthenticateCustomerUseCase.InputValues input = Mappers
            .getMapper(AuthenticateCustomerUseCaseInputMapper.class)
            .map(signInRequest);


        // and
        doReturn(input)
            .when(authenticateCustomerUseCaseInputMapper)
            .map(eq(signInRequest));

        // and
        doThrow(new UsernameNotFoundException("Error"))
                .when(authenticateCustomerUseCase)
                .execute(eq(input));

        // when
        RequestBuilder request = asyncPostRequest("/Customer/auth", payload);

        // then
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Error")));
    }

    @Test
    public void signUpReturnsBadRequestWhenEmailIsAlreadyBeenUsed() throws Exception {
        // given
        final SignUpRequest signUpRequest = new SignUpRequest("name", "email@email.com", "address", "password");
        String payload = signUpJson.write(signUpRequest).getJson();
        CreateCustomerUseCase.InputValues inputValues = CreateCustomerUseCase.InputValues.builder().build();

        // and
        doReturn(inputValues)
                .when(createCustomerInputMapper)
                .map(eq(signUpRequest));

        // and
        doThrow(new EmailAlreadyUsedException("Error"))
                .when(createCustomerUseCase)
                .execute(inputValues);
        // when
        RequestBuilder request = asyncPostRequest("/Customer", payload);

        // then
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Error")));
    }

    @Test
    public void signUpReturnsCreatedWhenIsANewCustomer() throws Exception {
        // given
        final SignUpRequest signUpRequest = new SignUpRequest("name", "email@email.com", "address", "password");
        String payload = signUpJson.write(signUpRequest).getJson();
        Customer customer = TestCoreEntityGenerator.randomCustomer();

        CreateCustomerUseCase.InputValues input =
            CreateCustomerUseCase.InputValues.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .password(customer.getPassword())
                .build();

        CreateCustomerUseCase.OutputValues output = CreateCustomerUseCase.OutputValues.builder().customer(customer).build();

        // and
        doReturn(input)
                .when(createCustomerInputMapper)
                .map(eq(signUpRequest));

        // and
        doReturn(output)
                .when(createCustomerUseCase)
                .execute(eq(input));

        // and
        doReturn(ApiResponse.builder().message("registered successfully").success(true).build())
            .when(CreateCustomerUseCaseOutputMapper)
            .map(customer);

        // when
        RequestBuilder request = asyncPostRequest("/Customer", payload);

        // then
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(header().string("location", "http://localhost/Customer/" + customer.getId().getNumber()))
                .andExpect(jsonPath("$.message", is("registered successfully")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        ;
    }
}