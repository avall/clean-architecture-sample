package com.delivery.presenter.usecases.security;

import com.delivery.core.usecases.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateCustomerUseCase implements UseCase<AuthenticateCustomerUseCase.InputValues, AuthenticateCustomerUseCase.OutputValues> {
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;

    public AuthenticateCustomerUseCase(AuthenticationManager authenticationManager,
                                       JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Authentication authentication = authenticationManager.authenticate(input.getAuthenticationToken());

        return OutputValues.builder().jwtToken(jwtProvider.generateToken(authentication)).build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InputValues implements UseCase.InputValues {
        private UsernamePasswordAuthenticationToken authenticationToken;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OutputValues implements UseCase.OutputValues {
        private String jwtToken;
    }
}
