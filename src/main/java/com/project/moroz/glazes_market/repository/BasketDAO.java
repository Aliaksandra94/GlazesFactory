package com.project.moroz.glazes_market.repository;

import com.project.moroz.glazes_market.entity.Basket;
import com.project.moroz.glazes_market.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketDAO extends JpaRepository<Basket, Integer> {
    @Query(value = "from BasketItem where basket.user.id = :userId")
    List<BasketItem> returnListOfProductsInBasket(@Param("userId") int userId);

    Basket findByUserId(int userId);
}
