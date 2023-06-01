package com.magadiflo.book.security.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserManagementConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails1 = User.builder()
                .username("admin")
                .password("12345")
                .authorities("WRITE")
                .build();
        UserDetails userDetails2 = User.builder()
                .username("martin")
                .password("12345")
                .authorities("READ")
                .build();
        UserDetails userDetails3 = User.builder()
                .username("nophy")
                .password("12345")
                .authorities("READ", "WRITE", "DELETE")
                .build();

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(userDetails1);
        inMemoryUserDetailsManager.createUser(userDetails2);
        inMemoryUserDetailsManager.createUser(userDetails3);

        return inMemoryUserDetailsManager;
    }

}
