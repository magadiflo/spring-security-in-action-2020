package com.magadiflo.book.security.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @GetMapping(path = "/")
    public String main(OAuth2AuthenticationToken token) {
        LOG.info(String.valueOf(token.getPrincipal()));
        return "main.html";
    }
}
