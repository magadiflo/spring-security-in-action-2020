package com.magadiflo.book.security.app.controllers;

import com.magadiflo.book.security.app.model.Document;
import com.magadiflo.book.security.app.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping("/documents/{code}")
    public Document getDetails(@PathVariable String code) {
        return this.documentService.getDocument(code);
    }
}
