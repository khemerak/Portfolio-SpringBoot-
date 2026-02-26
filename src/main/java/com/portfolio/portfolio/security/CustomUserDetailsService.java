package com.portfolio.portfolio.security;

import com.portfolio.portfolio.model.AdminCredentials;
import com.portfolio.portfolio.repository.AdminCredentialsRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminCredentialsRepository adminCredentialsRepository;

    public CustomUserDetailsService(AdminCredentialsRepository adminCredentialsRepository) {
        this.adminCredentialsRepository = adminCredentialsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminCredentials credentials = adminCredentialsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User.builder()
                .username(credentials.getUsername())
                .password(credentials.getPassword())
                .roles("ADMIN")
                .build();
    }
}
