package com.magadiflo.book.security.app.controllers;

import com.magadiflo.book.security.app.model.Employee;
import com.magadiflo.book.security.app.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping(path = "/book/details/{name}")
    public Employee getDetails(@PathVariable String name) {
        return this.bookService.getBookDetails(name);
    }
}
