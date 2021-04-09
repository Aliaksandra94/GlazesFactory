package com.project.moroz.glazes_market.service;

import com.project.moroz.glazes_market.entity.*;
import com.project.moroz.glazes_market.repository.ProductDAO;
import com.project.moroz.glazes_market.repository.RawMaterialDAO;
import com.project.moroz.glazes_market.repository.RawMaterialItemDAO;
import com.project.moroz.glazes_market.service.interfaces.RawMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RawMaterialServiceImpl implements RawMaterialService {
    private RawMaterialDAO rawMaterialDAO;
    private RawMaterialItemDAO rawMaterialItemDAO;
    private ProductDAO productDAO;

    @Autowired
    public void setRawMaterialItemDAO(RawMaterialItemDAO rawMaterialItemDAO) {
        this.rawMaterialItemDAO = rawMaterialItemDAO;
    }

    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Autowired
    public void setRawMaterialItem(RawMaterialItemDAO rawMaterialItemDAO) {
        this.rawMaterialItemDAO = rawMaterialItemDAO;
    }

    @Autowired
    public void setRawMaterialDAO(RawMaterialDAO rawMaterialDAO) {
        this.rawMaterialDAO = rawMaterialDAO;
    }

    @Override
    public List<RawMaterial> returnAllRawMaterials() {
        return rawMaterialDAO.findAll();
    }

    @Override
    public List<RawMaterial> returnAllRawMaterialsByCategoryId(int categoryId) {
        return rawMaterialDAO.findByCategoryId(categoryId);
    }

    @Override
    public RawMaterial returnRawMaterialById(int id) {
        return rawMaterialDAO.findByRawMaterialID(id);
    }

    @Override
    public Map<RawMaterial, Double> returnRawMaterialsAndQuantitiesForProductInProcess(List<Product> products) {
        Map<RawMaterial, Double> rawMaterialDoubleMap = new HashMap<>();
        List<RawMaterial> rawMaterials = new ArrayList<>();
        for (Product product : products) {
            List<RawMaterialItem> currentRawMaterialItems = product.getGlazesType().getRawMaterialItems();
            for (RawMaterialItem rawMaterialItem : currentRawMaterialItems) {
                RawMaterial rawMaterial = null;
                double quantity = 0;
                if (rawMaterials.isEmpty()) {
                    rawMaterial = rawMaterialItem.getRawMaterial();
                    rawMaterials.add(rawMaterial);
                    if (productDAO.findProductQuantityByOrderStageId(1, 4, product.getId()) == null) {
                        quantity = 0;
                    } else {
                        quantity = currentRawMaterialItems.get(0).getQuantity() * productDAO.findProductQuantityByOrderStageId(1, 4, product.getId());
                    }
                } else {
                    if (rawMaterials.contains(rawMaterialItem.getRawMaterial())) {
                        for (Map.Entry<RawMaterial, Double> item : rawMaterialDoubleMap.entrySet()) {
                            if (item.getKey().getRawMaterialID() != rawMaterialItem.getRawMaterial().getRawMaterialID()) {
                                continue;
                            } else {
                                if (productDAO.findProductQuantityByOrderStageId(1, 4, product.getId()) == null) {
                                    rawMaterial = item.getKey();
                                    quantity = item.getValue();
                                    break;
                                } else {
                                    rawMaterial = item.getKey();
                                    quantity = item.getValue() + rawMaterialItem.getQuantity() * productDAO.findProductQuantityByOrderStageId(1, 4, product.getId());
                                    //rawMaterialDoubleMap.replace(rawMaterialItem.getRawMaterial(), item.getValue(), item.getValue()+rawMaterialItem.getQuantity()*productDAO.findProductQuantityByOrderStageId(1, 4, product.getId()));
                                }
                            }
                        }
                    } else {
                        rawMaterial = rawMaterialItem.getRawMaterial();
                        rawMaterials.add(rawMaterial);
                        if (productDAO.findProductQuantityByOrderStageId(1, 4, product.getId()) == null) {
                            quantity = 0;
                        } else {
                            quantity = rawMaterialItem.getQuantity() * productDAO.findProductQuantityByOrderStageId(1, 4, product.getId());
                        }
                    }
                }
                if (rawMaterialDoubleMap.isEmpty()) {
                    rawMaterialDoubleMap.put(rawMaterial, quantity);
                } else {
                    for (Map.Entry<RawMaterial, Double> item : rawMaterialDoubleMap.entrySet()) {
                        if (item.getKey().equals(rawMaterial)) {
                            rawMaterialDoubleMap.replace(rawMaterialItem.getRawMaterial(), item.getValue(), quantity);
                            break;
                        } else {
                            continue;
                        }
                    }
                    if (rawMaterialDoubleMap.containsKey(rawMaterial)) {
                        continue;
                    } else {
                        rawMaterialDoubleMap.put(rawMaterial, quantity);
                    }
                }
            }
        }
        return rawMaterialDoubleMap;
    }

    @Override
    public Map<RawMaterial, Double> returnRawMaterialsAndQuantitiesNeedsForProduceProducts(List<OrderItem> orderItems) {
        Map<RawMaterial, Double> rawMaterialDoubleMap = new HashMap<>();
        List<RawMaterial> rawMaterials = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            List<RawMaterialItem> currentRawMaterialItems = orderItem.getProduct().getGlazesType().getRawMaterialItems();
            for (RawMaterialItem rawMaterialItem : currentRawMaterialItems) {
                RawMaterial rawMaterial = null;
                double quantity = 0;
                if (rawMaterials.isEmpty()) {
                    rawMaterial = rawMaterialItem.getRawMaterial();
                    rawMaterials.add(rawMaterial);
                    quantity = currentRawMaterialItems.get(0).getQuantity() * orderItem.getQuantity();
                } else {
                    if (rawMaterials.contains(rawMaterialItem.getRawMaterial())) {
                        for (Map.Entry<RawMaterial, Double> item : rawMaterialDoubleMap.entrySet()) {
                            if (item.getKey().getRawMaterialID() != rawMaterialItem.getRawMaterial().getRawMaterialID()) {
                                continue;
                            } else {
                                rawMaterial = item.getKey();
                                quantity = item.getValue() + rawMaterialItem.getQuantity() * orderItem.getQuantity();
                            }
                        }
                    } else {
                        rawMaterial = rawMaterialItem.getRawMaterial();
                        rawMaterials.add(rawMaterial);
                        quantity = rawMaterialItem.getQuantity() * orderItem.getQuantity();
                    }
                }
                if (rawMaterialDoubleMap.isEmpty()) {
                    rawMaterialDoubleMap.put(rawMaterial, quantity);
                } else {
                    for (Map.Entry<RawMaterial, Double> item : rawMaterialDoubleMap.entrySet()) {
                        if (item.getKey().equals(rawMaterial)) {
                            rawMaterialDoubleMap.replace(rawMaterialItem.getRawMaterial(), item.getValue(), quantity);
                            break;
                        } else {
                            continue;
                        }
                    }
                    if (rawMaterialDoubleMap.containsKey(rawMaterial)) {
                        continue;
                    } else {
                        rawMaterialDoubleMap.put(rawMaterial, quantity);
                    }
                }
            }
        }
        return rawMaterialDoubleMap;
    }

    @Override
    public boolean isEnoughRaw(List<OrderItem> orderItems) {
        boolean isEnoughRaw = true;
        List<RawMaterial> rawMaterials = new ArrayList<>();
        for (Map.Entry<RawMaterial, Double> currentItem : returnRawMaterialsAreNotEnough(orderItems).entrySet()) {
            rawMaterials.add(currentItem.getKey());
        }
        int size = rawMaterials.size();
        if (size >= 1) {
            isEnoughRaw = false;
        } else {
            isEnoughRaw = true;
        }
        return isEnoughRaw;
    }

    @Override
    public Map<RawMaterial, Double> returnRawMaterialsAreNotEnough(List<OrderItem> orderItems) {
        Map<RawMaterial, Double> listOfRawWhichNotEnough = new HashMap<>();
        Map<RawMaterial, Double> currentOrder = returnRawMaterialsAndQuantitiesNeedsForProduceProducts(orderItems);
        List<Product> products = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            products.add(orderItem.getProduct());
        }
        Map<RawMaterial, Double> otherOrders = returnRawMaterialsAndQuantitiesForProductInProcess(products);
        for (Map.Entry<RawMaterial, Double> currentItem : currentOrder.entrySet()) {
            for (Map.Entry<RawMaterial, Double> otherItem : otherOrders.entrySet()) {
                if (currentItem.getKey().equals(otherItem.getKey())) {
                    if ((currentItem.getKey().getQuantity() - otherItem.getValue()) <= 0) {
                        listOfRawWhichNotEnough.put(currentItem.getKey(), currentItem.getKey().getQuantity() - otherItem.getValue());
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
        }
        return listOfRawWhichNotEnough;
    }

    @Override
    public double returnRawMaterialQuantityAreNotEnough(int rawId, Order order) {
        double quantity = 0;
        Map<RawMaterial, Double> listOfRawWhichNotEnough = returnRawMaterialsAreNotEnough(order.getOrderItems());
        for (Map.Entry<RawMaterial, Double> item : listOfRawWhichNotEnough.entrySet()) {
            if (item.getKey().getRawMaterialID() == rawId) {
                quantity = item.getValue();
            }
        }
        return quantity;
    }

    @Override
    public void updateRawMaterialQuantity(int rawId, int quantity) {
        RawMaterial rawMaterial = rawMaterialDAO.findByRawMaterialID(rawId);
        rawMaterial.setQuantity(quantity);
    }
}
