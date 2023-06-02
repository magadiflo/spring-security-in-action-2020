package com.magadiflo.book.security.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping(path = "/a")
    public String postEndPointA() {
        return "Works!";
    }

    @GetMapping(path = "/a")
    public String getEndPointA() {
        return "Works!";
    }

    @GetMapping(path = "/a/b")
    public String getEndPointB() {
        return "Works!";
    }

    @GetMapping(path = "/a/b/c")
    public String getEndPointC() {
        return "Works!";
    }
}
