package com.project.moroz.glazes_market.entity;

import javax.persistence.*;

@Entity
@Table(name = "raw_material_items")
public class RawMaterialItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glaze_type_id", nullable = false,
            foreignKey = @ForeignKey(name = "raw_material_item_glaze_type"))
    private GlazesType glazesType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raw_material_id", nullable = false,
            foreignKey = @ForeignKey(name = "raw_material_item_raw_material"))
    private RawMaterial rawMaterial;

    @Transient
    private double amount;

    public RawMaterialItem() {
    }

    public RawMaterialItem(int id, double quantity, double price,
                           GlazesType glazesType, RawMaterial rawMaterial,
                           double amount) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.glazesType = glazesType;
        this.rawMaterial = rawMaterial;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public GlazesType getGlazesType() {
        return glazesType;
    }

    public void setGlazesType(GlazesType glazesType) {
        this.glazesType = glazesType;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public double getAmount() {
        return this.rawMaterial.getPrice() * this.quantity;
    }
}
