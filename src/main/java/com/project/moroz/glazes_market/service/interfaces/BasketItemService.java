package com.project.moroz.glazes_market.service.interfaces;

import com.project.moroz.glazes_market.entity.BasketItem;

public interface BasketItemService {
    BasketItem returnBasketItemById(int id);

    void deleteBasketItem(BasketItem basketItem);
}
