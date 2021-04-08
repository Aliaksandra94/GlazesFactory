package com.project.moroz.glazes_market.repository;

import com.project.moroz.glazes_market.entity.Product;
import com.project.moroz.glazes_market.entity.RawMaterial;
import com.project.moroz.glazes_market.entity.RawMaterialItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RawMaterialItemDAO extends JpaRepository<RawMaterial, Integer> {

    @Query(value = "select oi.quantity from OrderItem oi where not oi.order.orderStage.orderStageID =:orderStageId1 " +
            "and not oi.order.orderStage.orderStageID =:orderStageId2")
    List<Double> findSumRawMaterialsByOrderStageId(int orderStageId1, int orderStageId2);


}
