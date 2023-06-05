package com.magadiflo.book.security.app.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.*;
import java.io.IOException;

public class CsrfTokenLogger implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(CsrfTokenLogger.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        Object csrfObject = request.getAttribute("_csrf");
        CsrfToken csrfToken = (CsrfToken) csrfObject;

        LOG.info("CSRF token: {}", csrfToken.getToken());

        filterChain.doFilter(request, response);
    }
}
