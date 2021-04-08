package com.project.moroz.glazes_market.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "basket_items")
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToMany(mappedBy = "basketItems", fetch = FetchType.EAGER)
    private List<Product> products;

    public BasketItem() {
    }

    public BasketItem(int id, int quantity, Basket basket, List<Product> products) {
        this.id = id;
        this.quantity = quantity;
        this.basket = basket;
        this.products = products;
    }

    public BasketItem(Basket basket, int quantity) {
        this.basket=basket;
        this.quantity=quantity;
    }

    public BasketItem(int quantity) {
        this.quantity=quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}