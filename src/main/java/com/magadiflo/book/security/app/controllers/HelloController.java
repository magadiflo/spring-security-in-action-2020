package com.magadiflo.book.security.app.controllers;

import com.magadiflo.book.security.app.services.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {
    @Autowired
    private NameService nameService;

    @GetMapping(path = "/hello")
    public String hello() {
        return String.format("Hello, %s", this.nameService.getName());
    }

    @GetMapping(path = "/secret/names/{name}")
    public List<String> names(@PathVariable String name) {
        return this.nameService.getSecretNames(name);
    }
}
