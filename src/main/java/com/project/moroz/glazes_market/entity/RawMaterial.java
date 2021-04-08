package com.project.moroz.glazes_market.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "raw_materials")
public class RawMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "raw_material_id")
    private int rawMaterialID;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "price")
    private double price;

    @Column(name = "delivery_time")
    private int deliveryTime;

    @OneToMany(mappedBy = "rawMaterial")
    private List<RawMaterialItem> rawMaterialItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false,
            foreignKey = @ForeignKey(name = "raw_material_category"))
    private Category category;


    public RawMaterial() {
    }

    public RawMaterial(int rawMaterialID, String name, int quantity, double price,
                       int deliveryTime, List<RawMaterialItem> rawMaterialItems, Category category) {
        this.rawMaterialID = rawMaterialID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.deliveryTime = deliveryTime;
        this.rawMaterialItems = rawMaterialItems;
        this.category=category;
    }

    public int getRawMaterialID() {
        return rawMaterialID;
    }

    public void setRawMaterialID(int rawMaterialID) {
        this.rawMaterialID = rawMaterialID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public List<RawMaterialItem> getRawMaterialItems() {
        return rawMaterialItems;
    }

    public void setRawMaterialItems(List<RawMaterialItem> rawMaterialItems) {
        this.rawMaterialItems = rawMaterialItems;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}