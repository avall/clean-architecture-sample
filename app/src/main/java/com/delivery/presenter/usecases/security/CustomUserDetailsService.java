package com.delivery.presenter.usecases.security;

import com.delivery.core.repositories.ICustomerRepository;
import com.delivery.database.entities.CustomerDb;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private ICustomerRepository customerRepository;

    public CustomUserDetailsService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomerDb customerData = customerRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", email)));

        return UserPrincipal.from(customerData);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        CustomerDb customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", id)));

        return UserPrincipal.from(customer);
    }
}
