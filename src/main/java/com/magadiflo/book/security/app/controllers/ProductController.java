package com.magadiflo.book.security.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping(path = "/products/{code}")
    public String productCode(@PathVariable String code) {
        return code;
    }
}
