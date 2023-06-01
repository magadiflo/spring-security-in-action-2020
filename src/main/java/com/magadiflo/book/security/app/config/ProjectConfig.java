package com.magadiflo.book.security.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

//        http.authorizeRequests().anyRequest().hasAuthority("WRITE");
//        http.authorizeRequests().anyRequest().hasAnyAuthority("WRITE", "READ");

        String expression = "hasAuthority('READ') and !hasAuthority('DELETE')";
        http.authorizeRequests().anyRequest().access(expression);
    }
}
