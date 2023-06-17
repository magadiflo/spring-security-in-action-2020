package com.magadiflo.book.security.app.service;

import com.magadiflo.book.security.app.model.Product;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @PreFilter("filterObject.owner == authentication.name")
    public List<Product> sellProducts(List<Product> products) {
        // Vende productos y retorna la lista de productos vendidos
        return products;
    }
}
