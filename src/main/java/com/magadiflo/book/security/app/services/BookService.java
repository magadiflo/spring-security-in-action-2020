package com.magadiflo.book.security.app.services;

import com.magadiflo.book.security.app.model.Employee;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookService {

    private Map<String, Employee> records = Map.of(
            "admin", new Employee("Nophy Díaz",
                    List.of("El caballero carmelo", "Paco Yunke"),
                    List.of("accountant", "reader")),
            "martin", new Employee("Martín Díaz",
                    List.of("Spring Security In Action", "Java 17"),
                    List.of("researcher"))
    );

    @PostAuthorize("returnObject.roles.contains('reader')")
    public Employee getBookDetails(String name) {
        return this.records.get(name);
    }

}
