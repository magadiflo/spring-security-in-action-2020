package com.magadiflo.book.security.app.config;

import com.magadiflo.book.security.app.filters.StaticKeyAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private StaticKeyAuthenticationFilter customFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(this.customFilter, BasicAuthenticationFilter.class)
                .authorizeRequests().anyRequest().permitAll();
    }
}
