package com.project.moroz.glazes_market.repository;

import com.project.moroz.glazes_market.entity.GlazesType;
import com.project.moroz.glazes_market.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {
List<Product> findAllByGlazesTypeId(int glazesTypeID);
    @Query(value = "select oi.product from OrderItem oi where not oi.order.orderStage.orderStageID =:orderStageId1 " +
            "and not oi.order.orderStage.orderStageID =:orderStageId2 group by oi.product.id")
    List<Product> findProductByOrderStageId(int orderStageId1, int orderStageId2);
    @Query(value = "select sum (oi.product.quantity) from OrderItem oi where not oi.order.orderStage.orderStageID =:orderStageId1 " +
            "and not oi.order.orderStage.orderStageID =:orderStageId2 and oi.product.id =:productId")
    Integer findProductQuantityByOrderStageId(int orderStageId1, int orderStageId2, int productId);

//    @Query(value = "select oi.product from OrderItem oi where oi.order.orderStage.orderStageID =:orderStageId group by oi.product.id order by sum (oi.quantity) ASC")
//    List<Product> sortByQuantityReadyASC(int orderStageId);
//
//    @Query(value = "select oi.product from OrderItem oi where oi.order.orderStage.orderStageID =:orderStageId group by oi.product.id order by sum(oi.quantity) desc ")
//    List<Product> sortByQuantityReadyDESC(int orderStageId);
//
//    @Query(value = "select oi.product from OrderItem oi where not oi.order.orderStage.orderStageID =:orderStageId group by oi.product.id order by sum (oi.quantity) ASC")
//    List<Product> sortByQuantityOrderedASC(int orderStageId);
//
//    @Query(value = "select oi.product from OrderItem oi where not oi.order.orderStage.orderStageID =:orderStageId group by oi.product.id order by sum (oi.quantity) DESC ")
//    List<Product> sortByQuantityOrderedDESC(int orderStageId);
//
//
//    @Query(value = "select oi.product from OrderItem oi where oi.order.orderStage.orderStageID =:orderStageId group by oi.product.id order by sum (oi.amount) ASC")
//    List<Product> sortByAmountReadyASC(int orderStageId);
//
//    @Query(value = "select oi.product from OrderItem oi where oi.order.orderStage.orderStageID =:orderStageId group by oi.product.id order by sum(oi.amount) desc ")
//    List<Product> sortByAmountReadyDESC(int orderStageId);
//
//    @Query(value = "select oi.product from OrderItem oi where not oi.order.orderStage.orderStageID =:orderStageId group by oi.product.id order by sum (oi.amount) ASC")
//    List<Product> sortByAmountOrderedASC(int orderStageId);
//
//    @Query(value = "select oi.product from OrderItem oi where not oi.order.orderStage.orderStageID =:orderStageId group by oi.product.id order by sum (oi.amount) DESC ")
//    List<Product> sortByAmountOrderedDESC(int orderStageId);
}
