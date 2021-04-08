package com.project.moroz.glazes_market.repository;

import com.project.moroz.glazes_market.entity.OrderStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStageDAO extends JpaRepository<OrderStage, Integer> {
    @Query(value = "select os.orderStageID from OrderStage os")
    List<Integer> getAllId();
}
