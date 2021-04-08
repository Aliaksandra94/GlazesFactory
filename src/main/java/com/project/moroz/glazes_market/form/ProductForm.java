package com.project.moroz.glazes_market.form;

import com.project.moroz.glazes_market.model.Product;
import org.springframework.web.multipart.MultipartFile;

public class ProductForm {
    private int id;
    private String name;
    private double price;
    private boolean newProduct = false;

    public ProductForm() {
        this.newProduct = true;
    }

    public ProductForm(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isNewProduct() {
        return newProduct;
    }

    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }
}
