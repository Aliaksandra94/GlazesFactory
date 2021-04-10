package com.project.moroz.glazes_market.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 3, max = 15, message = "Name should be between 3 and 15 characters.")
    private String name;

    @Size(min = 3, max = 255, message = "Description should be between 3 and 255 characters.")
    private String description;

    @DecimalMin(value = "0.01", message = "Price should be equal or more than 0.01")
    @Max(value = 10000, message = "Price's length should be less than 5 characters")
    private double price;

    @Min(value = 0, message = "Quantity should be not less than 0 and should be integer.")
    private int quantity;

    @Column(name = "production_time")
    @Min(value = 1, message = "Production Time in hours should be more than 0 and should be integer.")
    private int productionTime;

    @Column(name = "cost")
    private double cost;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private GlazesType glazesType;

    @ManyToMany
    @JoinTable(name = "basket_item_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "basket_item_id"))
    private List<BasketItem> basketItems;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;


    public Product() {
    }

    public Product(int id, String name, String description, double price, int quantity,
            int productionTime, double cost, GlazesType glazesType, List<BasketItem> basketItems,
                   List<OrderItem> orderItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.productionTime = productionTime;
        this.cost = cost;
        this.glazesType = glazesType;
        this.basketItems = basketItems;
        this.orderItems = orderItems;
    }

    public Product(String name, String description, double price, int quantity,
            int productionTime, double cost, GlazesType glazesType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.productionTime = productionTime;
        this.cost = cost;
        this.glazesType = glazesType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(int productionTime) {
        this.productionTime = productionTime;
    }

    public GlazesType getGlazesType() {
        return glazesType;
    }

    public void setGlazesType(GlazesType glazesType) {
        this.glazesType = glazesType;
    }

    public List<BasketItem> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(List<BasketItem> basketItems) {
        this.basketItems = basketItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
