package com.project.moroz.glazes_market.service;


import com.project.moroz.glazes_market.entity.*;
import com.project.moroz.glazes_market.repository.GlazesTypeDAO;
import com.project.moroz.glazes_market.repository.OrderDAO;
import com.project.moroz.glazes_market.repository.OrderItemDAO;
import com.project.moroz.glazes_market.repository.ProductDAO;
import com.project.moroz.glazes_market.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductDAO productDAO;
    private GlazesTypeDAO glazesTypeDAO;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;

    @Autowired
    public void setOrderItemDAO(OrderItemDAO orderItemDAO) {
        this.orderItemDAO = orderItemDAO;
    }

    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Autowired
    public void setGlazesTypeDAO(GlazesTypeDAO glazesTypeDAO) {
        this.glazesTypeDAO = glazesTypeDAO;
    }

    @Autowired
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void updateAvailableQuantity(Product product, int quantity) {
        //Product product = productDAO.getOne(product1.getId());
        product.setQuantity(quantity);
        productDAO.save(product);
    }

    @Override
    @Transactional
    public void saveProductWithGlazeType(Product product, int glazesTypeId) {
        GlazesType glazesType = glazesTypeDAO.getOne(glazesTypeId);
        product.setGlazesType(glazesType);
        product.setCost(glazesType.getCost());
        List<RawMaterialItem> rawMaterialItems = glazesType.getRawMaterialItems();
        for (RawMaterialItem rawMaterialItem : rawMaterialItems) {
            rawMaterialItem.getRawMaterial().setQuantity(rawMaterialItem.getRawMaterial().getQuantity() - (rawMaterialItem.getQuantity() * product.getQuantity()));
        }
        productDAO.save(product);
    }

    @Override
    public void updateProductQuantityWithPlacedOrder(int productId, int quantity) {
        Product product = productDAO.getOne(productId);
        GlazesType glazesType = product.getGlazesType();
        List<RawMaterialItem> rawMaterialItems = glazesType.getRawMaterialItems();
        for (RawMaterialItem rawMaterialItem : rawMaterialItems) {
            rawMaterialItem.getRawMaterial().setQuantity(rawMaterialItem.getRawMaterial().getQuantity() - (rawMaterialItem.getQuantity() * product.getQuantity()));
        }
        product.setQuantity(quantity);
        productDAO.save(product);
    }

//    @Override
//    @Transactional
//    public void saveProduct(Product product) {
//        productDAO.save(product);
//    }

    @Override
    public void setGlazesType(int productId, int typeId) {
        Product product = productDAO.getOne(productId);
        GlazesType glazesType = glazesTypeDAO.getOne(typeId);
        product.setGlazesType(glazesType);
    }

    @Override
    @Transactional
    public void deleteProduct(Product product) {
        GlazesType glazesType = product.getGlazesType();
        List<RawMaterialItem> rawMaterialItems = glazesType.getRawMaterialItems();
        for (RawMaterialItem rawMaterialItem : rawMaterialItems) {
            rawMaterialItem.getRawMaterial().setQuantity(rawMaterialItem.getRawMaterial().getQuantity() + (rawMaterialItem.getQuantity() * product.getQuantity()));
        }
        productDAO.delete(product);
    }

    @Override
    @Transactional
    public List<Product> returnAllProducts() {
        return productDAO.findAll();
    }

    @Override
    @Transactional
    public Product returnProductById(int id) {
        return productDAO.getOne(id);
    }

    @Override
    @Transactional
    public void updateProductWithNewData(int productId, String productName, int typeId, String description,
                                         double productPrice, int quantity,
                                         int productionTime) {
        Product product = productDAO.getOne(productId);
        GlazesType glazesType = glazesTypeDAO.getOne(typeId);
        List<RawMaterialItem> rawMaterialItems = glazesType.getRawMaterialItems();
        product.setName(productName);
        product.setGlazesType(glazesType);
        product.setDescription(description);
        product.setPrice(productPrice);
        if (product.getQuantity() > quantity) {
            for (RawMaterialItem rawMaterialItem : rawMaterialItems) {
                rawMaterialItem.getRawMaterial().setQuantity(rawMaterialItem.getRawMaterial().getQuantity() + (rawMaterialItem.getQuantity() * (product.getQuantity() - quantity)));
            }
        } else if (product.getQuantity() < quantity) {
            for (RawMaterialItem rawMaterialItem : rawMaterialItems) {
                rawMaterialItem.getRawMaterial().setQuantity(rawMaterialItem.getRawMaterial().getQuantity() - (rawMaterialItem.getQuantity() * (product.getQuantity() - quantity)));
            }
        } else {
            product.setQuantity(quantity);
        }
        product.setProductionTime(productionTime);
        product.setCost(glazesType.getCost());
        productDAO.save(product);
    }

    @Override
    @Transactional
    public void updateProductQuantity(int orderId) {
        Order order = orderDAO.getOne(orderId);
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItemList.add(orderItem);
        }
        for (OrderItem orderItem : orderItemList) {
            Product product = orderItem.getProduct();
            int newQuantity = product.getQuantity() - orderItem.getQuantity();
            product.setQuantity(newQuantity);
            productDAO.save(product);
        }
    }

    @Override
    public Integer returnOrderedProductQuantity(int productId, int orderStageId) {
        return orderItemDAO.getOrderedQuantityOfTheProductByProductId(productId, orderStageId);
    }

    @Override
    public Product returnNewProduct() {
        Product product = new Product();
        productDAO.save(product);
        return product;
    }

    @Override
    public double getCostOfProduct(int id) {
        return 0;
    }

    @Override
    public Map<Product, List<Double>> returnProductWithQuantities(List<Product> products) {
        Map<Product, List<Double>> productWithAmountAndQuantities = new HashMap<>();
        for (Product product: products){
            List<Double> quantitiesAndAmount = new ArrayList<>();
            Integer readyQuantity = orderItemDAO.getReadyQuantityOfTheProductByProductId(product.getId(), 1);
            if (readyQuantity == null){
                quantitiesAndAmount.add(0.00);
            } else {
                quantitiesAndAmount.add((double)readyQuantity);
            }
            Double readyAVGPrice = orderItemDAO.getReadyAmountOfTheProductByProductId(product.getId(), 1);
            if(readyAVGPrice == null){
                quantitiesAndAmount.add(0.00);
            } else {
                quantitiesAndAmount.add(readyAVGPrice);
            }
            Integer orderedQuantity = orderItemDAO.getOrderedQuantityOfTheProductByProductId(product.getId(), 1);
            if (orderedQuantity == null){
                quantitiesAndAmount.add(0.00);
            } else {
                quantitiesAndAmount.add((double)orderedQuantity);
            }
            Double orderedAVRPrice = orderItemDAO.getOrderedAmountOfTheProductByProductId(product.getId(), 1);
            if (orderedAVRPrice == null){
                quantitiesAndAmount.add(0.00);
            } else {
                quantitiesAndAmount.add((double)orderedAVRPrice);
            }
            productWithAmountAndQuantities.put(product, quantitiesAndAmount);
        }
        return productWithAmountAndQuantities;
    }

    @Override
    public List<Product> returnAllByGlazesTypeId(int glazesTypeID) {
        return productDAO.findAllByGlazesTypeId(glazesTypeID);
    }

//    @Override
//    public List<Product> sortByQuantityReadyASC(int orderStageId) {
//        return productDAO.sortByQuantityReadyASC(orderStageId);
//    }
//
//
//    @Override
//    public List<Product> sortByQuantityReadyDESC(int orderStageId) {
//        return productDAO.sortByQuantityReadyDESC(orderStageId);
//    }
//
//    @Override
//    public List<Product> sortByQuantityOrderedASC(int orderStageId) {
//        return productDAO.sortByQuantityOrderedASC(orderStageId);
//    }
//
//    @Override
//    public List<Product> sortByQuantityOrderedDESC(int orderStageId) {
//        return productDAO.sortByQuantityOrderedDESC(orderStageId);
//    }
//
//    @Override
//    public List<Product> sortByAmountReadyASC(int orderStageId) {
//        return productDAO.sortByAmountReadyASC(orderStageId);
//    }
//
//    @Override
//    public List<Product> sortByAmountReadyDESC(int orderStageId) {
//        return productDAO.sortByAmountReadyDESC(orderStageId);
//    }
//
//    @Override
//    public List<Product> sortByAmountOrderedASC(int orderStageId) {
//        return productDAO.sortByAmountOrderedASC(orderStageId);
//    }
//
//    @Override
//    public List<Product> sortByAmountOrderedDESC(int orderStageId) {
//        return productDAO.sortByAmountOrderedDESC(orderStageId);
//    }
}