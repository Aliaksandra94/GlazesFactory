package com.project.moroz.glazes_market.service.interfaces;

import com.project.moroz.glazes_market.entity.Basket;
import com.project.moroz.glazes_market.entity.BasketItem;

import java.util.List;

public interface BasketService {
    void addProductToBasket(int userId, int productId);
    void addProductToBasketWithoutUser(int productId);

    void updateQuantity(int productId, int quantity);

    List<BasketItem> returnListOfProductsInBasket(int userId);

    void deleteItem(int userId, int idProduct);

    String returnTotalCostOfTheOrder(int userId);

    void cleanBasket(int userId);

    void submitProductInBasket(int userId, int productId);

    Basket getBasketOrCreate(int userId);

    Basket getBasketOrCreateWithoutUser();



}
