package com.project.moroz.glazes_market.service.interfaces;

import com.project.moroz.glazes_market.entity.Order;
import com.project.moroz.glazes_market.entity.Product;
import com.project.moroz.glazes_market.entity.User;
import com.project.moroz.glazes_market.form.UserForm;
import com.project.moroz.glazes_market.model.Basket;

import java.util.Date;
import java.util.List;

public interface OrderService {
    void saveOrder(Basket basket, int id);

    Order returnById(int id);

    List<Order> returnAllOrders();

    List<Order> returnAllOrdersByUserId(int userId);

    List<Order> returnAllOrdersByManagerId(int managerId);

    Order returnOrderById(int orderID);

    void savePersonalOrder(Product product, User user);

    List<Order> returnOrdersIncludeProduct(int id);
    List<Order> returnOrdersIncludeGlazesTypeId(int id);

    List<Order> returnAllBetweenDates(Date from, Date to);
    List<Order> returnOrdersByOrderStageId(int id);

    List<Order> returnOrdersWithoutOrderStageId(int id);
    List<Order> returnOrdersWithoutOrderStageIdByManagerId(int orderId, int managerId);
    List<Order> returnOrdersWithoutOrderStageIdByUserId(int orderId, int userId);
    List<Order> returnOrdersByOrderStageIdByManagerId(int orderId, int managerId);
    List<Order> returnOrdersByOrderStageIdByUserId(int orderId, int userId);

    Double returnSumOfOrders(int id);

    Double returnSumOfOrdersByUserAndStage(int userId, int orderStageId);
    List<Order> returnAllOrdersWithSortedDateASC();

    List<Order> returnAllOrdersWithSortedDateDesc();

    List<Order> returnAllOrdersByManagerIdWithSortedDateASC(int managerId);

    List<Order> returnAllOrdersByManagerIdWithSortedDateDESC(int managerId);

    List<Order> returnAllOrdersByOrderStageWithSortedDateASC(int orderStageId);

    List<Order> returnAllOrdersByOrderStageWithSortedDateDesc(int orderStageId);

    List<Order> returnAllOrdersWithSortedSumASC();

    List<Order> returnAllOrdersWithSortedSumDesc();

    List<Order> returnAllOrdersByManagerIdWithSortedSumASC(int managerId);

    List<Order> returnAllOrdersByManagerIdWithSortedSumDESC(int managerId);

    List<Order> returnAllOrdersByOrderStageWithSortedSumASC(int orderStageId);

    List<Order> returnAllOrdersByOrderStageWithSortedSumDesc(int orderStageId);
}
