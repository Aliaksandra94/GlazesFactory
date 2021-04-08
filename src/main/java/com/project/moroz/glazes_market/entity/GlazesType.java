package com.project.moroz.glazes_market.entity;

import com.project.moroz.glazes_market.model.BasketInfo;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "glaze_type")
public class GlazesType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "glazesType")
    private List<Product> productList;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @OneToMany(mappedBy = "glazesType")
    private List<RawMaterialItem> rawMaterialItems;

//    @ManyToMany(mappedBy = "glazesTypes")
//    private Set<RawMaterial> rawMaterialsList = new HashSet<>();

//    @OneToMany(mappedBy = "glazesTypes")
//    @MapKeyJoinColumn(name="raw_material_id")
//    private Map<Double, RawMaterial> recipe= new HashMap<Double, RawMaterial>();

    public GlazesType() {
    }

    public GlazesType(int id, String name, List<Product> productList,
                      Double cost,
                      List<RawMaterialItem> rawMaterialItems) {
        this.id = id;
        this.name = name;
        this.productList = productList;
        this.cost = cost;
        this.rawMaterialItems = rawMaterialItems;
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

//    public Map<Double, RawMaterial> getRecipe() {
//        return recipe;
//    }
//
//    public void setRecipe(Map<Double, RawMaterial> recipe) {
//        this.recipe = recipe;
//    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }


    public List<RawMaterialItem> getRawMaterialItems() {
        return rawMaterialItems;
    }

    public void setRawMaterialItems(List<RawMaterialItem> rawMaterialItems) {
        this.rawMaterialItems = rawMaterialItems;
    }
}