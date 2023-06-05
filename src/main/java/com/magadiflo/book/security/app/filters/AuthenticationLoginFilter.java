package com.magadiflo.book.security.app.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationLoginFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationLoginFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestId = request.getHeader("Request-Id");

        LOG.info("Successfully authenticated request with id: {}", requestId);

        filterChain.doFilter(request, response);
    }
}
