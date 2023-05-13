package com.magadiflo.book.security.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/greetings")
public class HelloController {
    @GetMapping(path = "/hello")
    public String hello() {
        return "Hello!";
    }
}
