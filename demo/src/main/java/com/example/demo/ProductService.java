package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public List<Product> search(String text) {
        return repo.findByNameContainingIgnoreCase(text);
    }

    public Product getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Product save(Product p) {
        return repo.save(p);
    }

    public Product update(String id, Product p) {
        p.setId(id);
        return repo.save(p);
    }

    public void deleteById(String id) {
        repo.deleteById(id);
    }

    public void initSampleData() {
        if (repo.count() > 0) return;

        String img1 = "img/img1.png";
        String img2 = "img/img2.png";
        String img3 = "img/img3.png";
        String img4 = "img/img4.png";
        String img5 = "img/img5.png";
        String img6 = "img/img6.png";

        String defaultDesc = "This product is excluded from all\npromotional discounts and offers.";

        List<Product> list = new ArrayList<>();
        list.add(new Product("4DFWD PULSE SHOES", "This product is excluded fr...", defaultDesc, "Adidas", "$160.00", img2));
        list.add(new Product("FORUM MID SHOES", "This product is excluded fr...", defaultDesc, "Adidas", "$100.00", img3));
        list.add(new Product("SUPERNOVA SHOES", "NMD City Stock 2", defaultDesc, "Adidas", "$150.00", img4));
        list.add(new Product("Adidas WHITE", "NMD City Stock 2", defaultDesc, "Adidas", "$160.00", img1));
        list.add(new Product("Adidas BLACK", "NMD City Stock 2", defaultDesc, "Adidas", "$120.00", img2));
        list.add(new Product("4DFWD PULSE RED", "This product is excluded fr...", defaultDesc, "Adidas", "$160.00", img1));
        list.add(new Product("4DFWD PULSE GREEN", "This product is excluded fr...", defaultDesc, "Adidas", "$160.00", img5));
        list.add(new Product("FORUM MID SHOES", "This product is excluded fr...", defaultDesc, "Adidas", "$100.00", img6));

        repo.saveAll(list);
    }
}
