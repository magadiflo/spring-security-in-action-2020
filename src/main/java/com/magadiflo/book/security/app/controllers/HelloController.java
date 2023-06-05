package com.magadiflo.book.security.app.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @PostMapping(path = "/hello")
    public String postHello() {
        return "Post hello!";
    }

    @PostMapping(path = "/ciao")
    public String postCiao() {
        return "Post ciao";
    }
}
