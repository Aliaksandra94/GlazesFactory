package com.project.moroz.glazes_market.service.interfaces;

import com.project.moroz.glazes_market.entity.*;

import java.util.List;
import java.util.Map;

public interface RawMaterialService {
    List<RawMaterial> returnAllRawMaterials();
    RawMaterial returnRawMaterialById(int id);
    List<RawMaterial> returnAllRawMaterialsByCategoryId(int categoryId);

    Map<RawMaterial, Double> returnRawMaterialsAndQuantitiesForProductInProcess(List<Product> products);

    Map<RawMaterial, Double> returnRawMaterialsAndQuantitiesNeedsForProduceProducts(List<OrderItem> orderItems);

    boolean isEnoughRaw(List<OrderItem> orderItems);

    Map<RawMaterial, Double> returnRawMaterialsAreNotEnough (List<OrderItem> orderItems);

    double returnRawMaterialQuantityAreNotEnough(int rawId, Order order);

    void updateRawMaterialQuantity(int rawId, int quantity);
}
