package com.magadiflo.book.security.app.config;

import com.magadiflo.book.security.app.entity.User;
import com.magadiflo.book.security.app.security.SecurityUser;
import com.magadiflo.book.security.app.services.InMemoryUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class UserManagementConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("12345");
        user.setAuthority("read");

        UserDetails userDetails = new SecurityUser(user);
        List<UserDetails> userDetailsList = List.of(userDetails);

        return new InMemoryUserDetailsService(userDetailsList);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
