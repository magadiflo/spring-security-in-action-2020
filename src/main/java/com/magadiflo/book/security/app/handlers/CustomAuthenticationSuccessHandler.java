package com.magadiflo.book.security.app.handlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        var authorities = authentication.getAuthorities();
        var auth = authorities.stream()
                .filter(grantedAuthority -> grantedAuthority.getAuthority().equals("read"))
                .findFirst();

        if (auth.isPresent()) {
            response.sendRedirect("/home");
        } else {
            response.sendRedirect("/error");
        }

    }
}
