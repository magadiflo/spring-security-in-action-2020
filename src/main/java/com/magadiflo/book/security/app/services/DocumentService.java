package com.magadiflo.book.security.app.services;

import com.magadiflo.book.security.app.model.Document;
import com.magadiflo.book.security.app.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @PostAuthorize("hasPermission(returnObject, 'ROLE_admin')")
    public Document getDocument(String code) {
        return this.documentRepository.findDocument(code);
    }
}
