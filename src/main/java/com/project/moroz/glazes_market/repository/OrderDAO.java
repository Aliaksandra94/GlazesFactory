package com.project.moroz.glazes_market.repository;

import com.project.moroz.glazes_market.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<Order, Integer> {
    Order findById(int id);

    List<Order> findByUserId(int userId);

    @Query(value = "select user.orders from User user where user.manager.id =:managerId")
    List<Order> findByManagerId(int managerId);

    @Query(value = "select stage.orders from OrderStage stage where stage.orderStageID =:id")
    List<Order> findAllByOrderStageId(int id);

    @Query(value = "from Order o where o.orderStage.orderStageID =:orderId and o.user.manager.id =:managerId")
    List<Order> findAllByOrderStageIdAndByManagerId(int orderId, int managerId);

    @Query(value = "from Order o where o.orderStage.orderStageID =:orderId and o.user.id =:userId")
    List<Order> findAllByOrderStageIdAndByUserId(int orderId, int userId);

    @Query(value = "select stage.orders from OrderStage stage where not stage.orderStageID =:id")
    List<Order> findAllWithoutOrderStageId(int id);

    @Query(value = "from Order order where order.orderDate between :from and :to")
    List<Order> findAllBetweenDates(Date from, Date to);

    @Query(value = "from Order o where not o.orderStage.orderStageID =:orderId and o.user.manager.id =:managerId")
    List<Order> findAllWithoutOrderStageIdAndByManagerId(int orderId, int managerId);

    @Query(value = "from Order o where not o.orderStage.orderStageID =:orderId and o.user.id =:userId")
    List<Order> findAllWithoutOrderStageIdAndByUserId(int orderId, int userId);

    @Query(value = "select SUM (o.amount) from Order o where o.user.id =:userId")
    Double getTotalAmountOfOrdersByUser(int userId);

    @Query(value = "select SUM (o.amount) from Order o where o.user.id =:userId and o.orderStage.orderStageID =:orderStageId")
    Double getTotalAmountOfOrdersByUserAndOrderStage(int userId, int orderStageId);

    @Query(value = "from Order o order by o.orderDate ASC")
    List<Order> findAllOrdersWithSortedDateASC();

    @Query(value = "from Order o order by o.orderDate desc")
    List<Order> findAllOrdersWithSortedDateDesc();

//    @Query(value = "select user.orders from User user where user.manager.id =:managerId order by ")
//    List<Order> findByManagerIdWithSortedDateASC(int managerId);

    @Query(value = "from Order o where o.user.manager.id =:managerId order by o.orderDate ASC")
    List<Order> findByManagerIdWithSortedDateASC(int managerId);

    @Query(value = "from Order o where o.user.manager.id =:managerId order by o.orderDate DESC ")
    List<Order> findByManagerIdWithSortedDateDESC(int managerId);

    @Query(value = "from Order o where o.orderStage.orderStageID =:orderStageId order by o.orderDate ASC")
    List<Order> findAllOrdersByOrderStageWithSortedDateASC(int orderStageId);

    @Query(value = "from Order o where o.orderStage.orderStageID =:orderStageId order by o.orderDate DESC")
    List<Order> findAllOrdersByOrderStageWithSortedDateDesc(int orderStageId);

    @Query(value = "from Order o order by o.amount ASC")
    List<Order> findAllOrdersWithSortedSumASC();

    @Query(value = "from Order o order by o.amount desc")
    List<Order> findAllOrdersWithSortedSumDesc();

    @Query(value = "from Order o where o.user.manager.id =:managerId order by o.amount ASC")
    List<Order> findByManagerIdWithSortedSumASC(int managerId);

    @Query(value = "from Order o where o.user.manager.id =:managerId order by o.amount DESC ")
    List<Order> findByManagerIdWithSortedSumDESC(int managerId);

    @Query(value = "from Order o where o.orderStage.orderStageID =:orderStageId order by o.amount ASC")
    List<Order> findAllOrdersByOrderStageWithSortedSumASC(int orderStageId);

    @Query(value = "from Order o where o.orderStage.orderStageID =:orderStageId order by o.amount DESC")
    List<Order> findAllOrdersByOrderStageWithSortedSumDesc(int orderStageId);
}