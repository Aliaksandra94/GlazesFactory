package com.project.moroz.glazes_market.repository;

import com.project.moroz.glazes_market.entity.Order;
import com.project.moroz.glazes_market.entity.OrderItem;
import com.project.moroz.glazes_market.entity.Product;
import com.project.moroz.glazes_market.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {
    @Query(value = "select oi.order from OrderItem oi where oi.product.id =:id")
    List<Order> findAllByProductId(int id);

    @Query(value = "select oi.order from OrderItem oi where oi.product.glazesType.id =:id")
    List<Order> findAllByGlazesTypeId(int id);

    @Query(value = "select SUM (oi.quantity) from OrderItem oi where oi.product.id =:id and not oi.order.orderStage.orderStageID =:orderStageId")
    Integer getOrderedQuantityOfTheProductByProductId(int id, int orderStageId);

    @Query(value = "select AVG (oi.amount) from OrderItem oi where oi.product.id =:id and not oi.order.orderStage.orderStageID =:orderStageId")
    Double getOrderedAmountOfTheProductByProductId(int id, int orderStageId);

    @Query(value = "select SUM (oi.quantity) from OrderItem oi where oi.product.id =:id and oi.order.orderStage.orderStageID =:orderStageId")
    Integer getReadyQuantityOfTheProductByProductId(int id, int orderStageId);

    @Query(value = "select avg (oi.amount) from OrderItem oi where oi.product.id =:id and oi.order.orderStage.orderStageID =:orderStageId")
    Double getReadyAmountOfTheProductByProductId(int id, int orderStageId);
}
