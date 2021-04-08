package com.project.moroz.glazes_market.model;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private int orderNum;
    private User userInfo;
    private final List<BasketInfo> basketInfos = new ArrayList<BasketInfo>();

    public Basket() {
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public List<BasketInfo> getBasketInfos() {
        return basketInfos;
    }

    private BasketInfo findById(int id) {
        for (BasketInfo basketInfo : this.basketInfos) {
            if (basketInfo.getProduct().getId() == id) {
                return basketInfo;
            }
        }
        return null;
    }

    public void addProduct(Product product, int quantity) {
        BasketInfo basketInfo = this.findById(product.getId());

        if (basketInfo == null) {
            basketInfo = new BasketInfo();
            basketInfo.setQuantity(0);
            basketInfo.setProduct(product);
            this.basketInfos.add(basketInfo);
        }
        int newQuantity = basketInfo.getQuantity() + quantity;
        if (newQuantity <= 0) {
            this.basketInfos.remove(basketInfo);
        } else {
            basketInfo.setQuantity(newQuantity);
        }
    }

    public void validate() {

    }

    public void updateProduct(int productID, int quantity) {
        BasketInfo basketInfo = this.findById(productID);
        if (basketInfo != null) {
            if (quantity <= 0) {
                this.basketInfos.remove(basketInfo);
            } else {
                basketInfo.setQuantity(quantity);
            }
        }
    }

    public void removeProduct(Product product) {
        BasketInfo basketInfo = this.findById(product.getId());
        if (basketInfo != null) {
            this.basketInfos.remove(basketInfo);
        }
    }

    public boolean isEmpty() {
        return this.basketInfos.isEmpty();
    }

    public boolean isValidUser() {
        return this.userInfo != null && this.userInfo.isValid();
    }

    public int getQuantityTotal() {
        int quantity = 0;
        for (BasketInfo basketInfo : this.basketInfos) {
            quantity += basketInfo.getQuantity();
        }
        return quantity;
    }

    public double getAmountTotal() {
        double total = 0;
        for (BasketInfo basketInfo : this.basketInfos) {
            total += basketInfo.getAmount();
        }
        return total;
    }

    public void updateQuantity(Basket basketForm) {
        if (basketForm != null) {
            List<BasketInfo> basketInfos = basketForm.getBasketInfos();
            for (BasketInfo basketInfo : basketInfos) {
                this.updateProduct(basketInfo.getProduct().getId(), basketInfo.getQuantity());
            }
        }

    }
}