package com.project.moroz.glazes_market.model;

public class BasketInfo {
    private Product product;
    private int quantity;

    public BasketInfo() {
        this.quantity = 0;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {this.quantity = quantity; }

    public int getQuantity() {
        return quantity;
    }

    public double getAmount() {
        return this.product.getPrice() * this.quantity;
    }
}
