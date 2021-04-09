package com.project.moroz.glazes_market.service.interfaces;

import com.project.moroz.glazes_market.entity.GlazesType;
import com.project.moroz.glazes_market.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> returnAllByGlazesTypeId(int glazesTypeID);
    void updateAvailableQuantity(Product product, int quantity);
    void saveProductWithGlazeType(Product product, int glazeTypeId);

    void updateProductQuantityWithPlacedOrder(int productId, int quantity);

    void setGlazesType(int productId, int glazeTypeId);

    void deleteProduct(Product product);

    List<Product> returnAllProducts();

    Product returnProductById(int id);

    void updateProductWithNewData(int productId, String productName, int typeId, String description,
                                  double productPrice, int quantity,
                                  int productionTime);

    void updateProductQuantity(int orderId, int orderStageId);

    Integer returnOrderedProductQuantity(int productId, int orderStageId);

    Map<Product, List<Double>> returnProductWithQuantities(List<Product> products);

    Product returnNewProduct();

    double getCostOfProduct(int id);

//    List<Product> sortByQuantityReadyASC(int orderStageId);
//
//    List<Product> sortByQuantityReadyDESC(int orderStageId);
//
//    List<Product> sortByQuantityOrderedASC(int orderStageId);
//
//    List<Product> sortByQuantityOrderedDESC(int orderStageId);
//
//    List<Product> sortByAmountReadyASC(int orderStageId);
//
//    List<Product> sortByAmountReadyDESC(int orderStageId);
//
//    List<Product> sortByAmountOrderedASC(int orderStageId);
//
//    List<Product> sortByAmountOrderedDESC(int orderStageId);
}