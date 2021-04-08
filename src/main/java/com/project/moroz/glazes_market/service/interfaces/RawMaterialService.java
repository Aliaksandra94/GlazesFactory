package com.project.moroz.glazes_market.service.interfaces;

import com.project.moroz.glazes_market.entity.Product;
import com.project.moroz.glazes_market.entity.RawMaterial;
import com.project.moroz.glazes_market.entity.RawMaterialItem;

import java.util.List;
import java.util.Map;

public interface RawMaterialService {
    List<RawMaterial> returnAllRawMaterials();
    List<RawMaterial> returnAllRawMaterialsByCategoryId(int categoryId);

    Map<RawMaterial, Double> returnRawMaterialsAndQuantitiesForProductInProcess(List<Product> products);
}
