package com.magadiflo.book.security.app.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NameService {
    @PreAuthorize("hasAuthority('write')")
    public String getName() {
        return "Systems!";
    }

    // Usa #name para representar el valor de los parámetros del método en la expresión de autorización
    @PreAuthorize("#name == authentication.principal.username")
    public List<String> getSecretNames(String name) {
        return this.secretName.get(name);
    }

    private final Map<String, List<String>> secretName = Map.of(
            "admin", List.of("Enérgico", "Perfecto"),
            "martin", List.of("Fantástico"));

}
