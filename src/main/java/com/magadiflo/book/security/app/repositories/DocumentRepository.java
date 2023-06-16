package com.magadiflo.book.security.app.repositories;

import com.magadiflo.book.security.app.model.Document;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class DocumentRepository {
    private Map<String, Document> documents = Map.of(
            "abc123", new Document("admin"),
            "def123", new Document("martin"),
            "ghi123", new Document("nophy"));

    public Document findDocument(String code) {
        return documents.get(code);
    }
}
