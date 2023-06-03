package com.magadiflo.book.security.app.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationLoginFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationLoginFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestId = httpRequest.getHeader("Request-Id");

        LOG.info("Successfully authenticated request with id: {}", requestId);

        filterChain.doFilter(request, response);
    }
}
