package com.project.moroz.glazes_market.service;

import com.project.moroz.glazes_market.entity.Order;
import com.project.moroz.glazes_market.entity.OrderItem;
import com.project.moroz.glazes_market.entity.Product;
import com.project.moroz.glazes_market.entity.User;
import com.project.moroz.glazes_market.model.Basket;
import com.project.moroz.glazes_market.model.BasketInfo;
import com.project.moroz.glazes_market.repository.*;
import com.project.moroz.glazes_market.service.interfaces.OrderService;
import com.project.moroz.glazes_market.utils.Utils;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private OrderStageDAO orderStageDAO;
    private ProductDAO productDAO;
    private OrderItemDAO orderItemDAO;


    @Autowired
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setOrderStageDAO(OrderStageDAO orderStageDAO) {
        this.orderStageDAO = orderStageDAO;
    }

    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Autowired
    public void setOrderItemDAO(OrderItemDAO orderItemDAO) {
        this.orderItemDAO = orderItemDAO;
    }

    @Override
    public void saveOrder(Basket basket, int id) {
        Order order = new Order();
        order.setUser(userDAO.getOne(id));
        order.setOrderDate(new Date((System.currentTimeMillis())));
        order.setOrderStage(orderStageDAO.getOne(4));
        order.setAmount(Precision.round(basket.getAmountTotal()-basket.getAmountTotal()*order.getUser().getDiscount()/100, 2));
        orderDAO.save(order);
        order.setOrderNumber(order.getId());

        List<BasketInfo> basketInfos = basket.getBasketInfos();

        for (BasketInfo basketInfo : basketInfos) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setAmount(Precision.round(basketInfo.getAmount()-basketInfo.getAmount()*order.getUser().getDiscount()/100, 2));
            orderItem.setPrice(Precision.round(basketInfo.getProduct().getPrice()-basketInfo.getProduct().getPrice()*order.getUser().getDiscount()/100, 2));
            orderItem.setQuantity(basketInfo.getQuantity());
            Product product = this.productDAO.getOne(basketInfo.getProduct().getId());
            orderItem.setProduct(product);
            orderItemDAO.save(orderItem);
        }
        basket.setOrderNum(order.getId());
    }

    @Override
    public Order returnById(int id) {
        return orderDAO.findById(id);
    }


    @Override
    public List<Order> returnAllOrders() {
        return orderDAO.findAll();
    }

    @Override
    public List<Order> returnAllOrdersByUserId(int userId) {
        return orderDAO.findByUserId(userId);
    }

    @Override
    public List<Order> returnAllOrdersByManagerId(int managerId) {
        return orderDAO.findByManagerId(managerId);
    }

    @Override
    public Order returnOrderById(int orderID) {
        return orderDAO.getOne(orderID);
    }

    @Override
    public void savePersonalOrder(Product product, User user) {
        Product product1 = productDAO.getOne(product.getId());
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date((System.currentTimeMillis())));
        order.setOrderStage(orderStageDAO.getOne(4));
        order.setAmount(Precision.round(product1.getQuantity()*product1.getPrice()-(product1.getQuantity()*product1.getPrice()*user.getDiscount()/100), 2));
        orderDAO.save(order);
        order.setOrderNumber(order.getId());
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setAmount(order.getAmount());
        orderItem.setPrice(product1.getPrice());
        orderItem.setQuantity(product1.getQuantity());
        orderItem.setProduct(product1);
        orderItemDAO.save(orderItem);
        product1.setQuantity(0);
    }

    @Override
    public List<Order> returnOrdersIncludeProduct(int id) {
        return orderItemDAO.findAllByProductId(id);
    }

    @Override
    public List<Order> returnOrdersIncludeGlazesTypeId(int id) {
        return orderItemDAO.findAllByGlazesTypeId(id);
    }

    @Override
    public List<Order> returnOrdersByOrderStageId(int id) {
        return orderDAO.findAllByOrderStageId(id);
    }

    @Override
    public List<Order> returnOrdersWithoutOrderStageId(int id) {
        return orderDAO.findAllWithoutOrderStageId(id);
    }

    @Override
    public List<Order> returnOrdersWithoutOrderStageIdByManagerId(int orderId, int managerId) {
        return orderDAO.findAllWithoutOrderStageIdAndByManagerId(orderId, managerId);
    }

    @Override
    public List<Order> returnOrdersByOrderStageIdByManagerId(int orderId, int managerId) {
        return orderDAO.findAllByOrderStageIdAndByManagerId(orderId, managerId);
    }

    @Override
    public List<Order> returnOrdersWithoutOrderStageIdByUserId(int orderId, int userId) {
        return orderDAO.findAllWithoutOrderStageIdAndByUserId(orderId, userId);
    }

    @Override
    public List<Order> returnOrdersByOrderStageIdByUserId(int orderId, int userId) {
        return orderDAO.findAllByOrderStageIdAndByUserId(orderId, userId);
    }

    @Override
    public List<Order> returnAllBetweenDates(Date from, Date to) {
        return orderDAO.findAllBetweenDates(from, to);
    }

    @Override
    public Double returnSumOfOrders(int id) {
        return orderDAO.getTotalAmountOfOrdersByUser(id);
    }

    @Override
    public Double returnSumOfOrdersByUserAndStage(int userId, int orderStageId) {
        return orderDAO.getTotalAmountOfOrdersByUserAndOrderStage(userId, orderStageId);
    }

    @Override
    public List<Order> returnAllOrdersWithSortedDateASC() {
        return orderDAO.findAllOrdersWithSortedDateASC();
    }

    @Override
    public List<Order> returnAllOrdersWithSortedDateDesc() {
        return orderDAO.findAllOrdersWithSortedDateDesc();
    }

    @Override
    public List<Order> returnAllOrdersByManagerIdWithSortedDateASC(int managerId) {
        return orderDAO.findByManagerIdWithSortedDateASC(managerId);
    }

    @Override
    public List<Order> returnAllOrdersByManagerIdWithSortedDateDESC(int managerId) {
        return orderDAO.findByManagerIdWithSortedDateDESC(managerId);
    }

    @Override
    public List<Order> returnAllOrdersByOrderStageWithSortedDateASC(int orderStageId) {
        return orderDAO.findAllOrdersByOrderStageWithSortedDateASC(orderStageId);
    }

    @Override
    public List<Order> returnAllOrdersByOrderStageWithSortedDateDesc(int orderStageId) {
        return orderDAO.findAllOrdersByOrderStageWithSortedDateDesc(orderStageId);
    }

    @Override
    public List<Order> returnAllOrdersWithSortedSumASC() {
        return orderDAO.findAllOrdersWithSortedSumASC();
    }

    @Override
    public List<Order> returnAllOrdersWithSortedSumDesc() {
        return orderDAO.findAllOrdersWithSortedSumDesc();
    }

    @Override
    public List<Order> returnAllOrdersByManagerIdWithSortedSumASC(int managerId) {
        return orderDAO.findByManagerIdWithSortedSumASC(managerId);
    }

    @Override
    public List<Order> returnAllOrdersByManagerIdWithSortedSumDESC(int managerId) {
        return orderDAO.findByManagerIdWithSortedSumDESC(managerId);
    }

    @Override
    public List<Order> returnAllOrdersByOrderStageWithSortedSumASC(int orderStageId) {
        return orderDAO.findAllOrdersByOrderStageWithSortedSumASC(orderStageId);
    }

    @Override
    public List<Order> returnAllOrdersByOrderStageWithSortedSumDesc(int orderStageId) {
        return orderDAO.findAllOrdersByOrderStageWithSortedSumDesc(orderStageId);
    }
}

