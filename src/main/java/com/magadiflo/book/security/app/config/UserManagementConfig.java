package com.magadiflo.book.security.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class UserManagementConfig {
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        String sqlGetUserByUsername = "SELECT usuario, pass, activo FROM usuarios WHERE usuario = ?";
        String sqlGetAuthorityByUser = "SELECT usuario, autoridad FROM autoridades WHERE usuario = ?";

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(sqlGetUserByUsername);
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(sqlGetAuthorityByUser);

        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
