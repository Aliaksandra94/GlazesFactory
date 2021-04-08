package com.project.moroz.glazes_market.repository;

import com.project.moroz.glazes_market.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketItemDAO extends JpaRepository<BasketItem, Integer> {
}
