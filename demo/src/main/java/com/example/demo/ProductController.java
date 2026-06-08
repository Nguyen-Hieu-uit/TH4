package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> list(@RequestParam(name = "q", required = false) String q) {
        if (q == null || q.isBlank()) return service.getAll();
        return service.search(q);
    }

    @GetMapping("/{id}")
    public Product get(@org.springframework.web.bind.annotation.PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public Product create(@org.springframework.web.bind.annotation.RequestBody Product p) {
        p.setId(null);
        return service.save(p);
    }

    @PutMapping("/{id}")
    public Product update(@org.springframework.web.bind.annotation.PathVariable String id, @org.springframework.web.bind.annotation.RequestBody Product p) {
        return service.update(id, p);
    }

    @DeleteMapping("/{id}")
    public void delete(@org.springframework.web.bind.annotation.PathVariable String id) {
        service.deleteById(id);
    }
}
