package com.example.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String shortDesc;
    private String longDesc;
    private String brand;
    private String price; // keep as string to match UI
    private String imageUrl;

    public Product() {}

    public Product(String name, String shortDesc, String longDesc, String brand, String price, String imageUrl) {
        this.name = name;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.brand = brand;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getShortDesc() { return shortDesc; }
    public void setShortDesc(String shortDesc) { this.shortDesc = shortDesc; }
    public String getLongDesc() { return longDesc; }
    public void setLongDesc(String longDesc) { this.longDesc = longDesc; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    @Override
    public String toString() {
        return "Product{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", price='" + price + '\'' + '}';
    }
}
