package com.project.moroz.glazes_market.service;

import com.project.moroz.glazes_market.entity.*;
import com.project.moroz.glazes_market.repository.ManagerDAO;
import com.project.moroz.glazes_market.repository.OrderStageDAO;
import com.project.moroz.glazes_market.service.interfaces.OrderService;
import com.project.moroz.glazes_market.service.interfaces.OrderStageService;
import com.project.moroz.glazes_market.service.interfaces.RawMaterialService;
import com.project.moroz.glazes_market.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class OrderStageServiceImpl implements OrderStageService {
    private OrderStageDAO orderStageDAO;
    private ManagerDAO managerDAO;
    private RawMaterialService rawMaterialService;

    @Autowired
    public void setRawMaterialService(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @Autowired
    public void setManagerDAO(ManagerDAO managerDAO) {
        this.managerDAO = managerDAO;
    }

    @Autowired
    public void setOrderStageDAO(OrderStageDAO orderStageDAO) {
        this.orderStageDAO = orderStageDAO;
    }

    @Override
    public List<OrderStage> returnAllOrderStage() {
        return orderStageDAO.findAll();
    }


    @Override
    public OrderStage returnOrderStageById(int orderStageId) {
        return orderStageDAO.getOne(orderStageId);
    }

    @Override
    public List<Integer> returnAllOrderStageId() {
        return orderStageDAO.getAllId();
    }

    @Override
    public List<OrderStage> returnAvailableOrderStageId(Order order, boolean role, HttpServletRequest request) {
        Set<Role> roles = managerDAO.findByLogin(Utils.getUserInSession(request).getLogin()).getRoles();
        List<OrderStage> orderStages = new ArrayList<>();
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            int count = 0;
            if (orderItem.getQuantity() >= orderItem.getProduct().getQuantity() && !role) {
                for (Role roleForCheck : roles) {
                    if (roleForCheck.getId() == 1 || roleForCheck.getId() == 2) {
                        orderStages.add(orderStageDAO.getOne(3));
                    } else if (roleForCheck.getId() == 3 && rawMaterialService.isEnoughRaw(orderItems)) {
                        orderStages.add(orderStageDAO.getOne(7));
                    }
                }
            } else if (orderItem.getQuantity() <= orderItem.getProduct().getQuantity() && !role) {
                count++;
                if (count == orderItems.size()) {
                    for (Role roleForCheck : roles) {
                        if (roleForCheck.getId() == 1 || roleForCheck.getId() == 2) {
                            orderStages.add(orderStageDAO.getOne(1));
                        }
                    }
                } else {
                    continue;
                }
            } else if (orderItem.getQuantity() >= orderItem.getProduct().getQuantity() && role) {
                if (!rawMaterialService.isEnoughRaw(orderItems)) {
                    orderStages.add(orderStageDAO.getOne(6));
                    break;
                } else if (rawMaterialService.isEnoughRaw(orderItems)) {
                    count++;
                    if (count == orderItems.size()) {
                        orderStages.add(orderStageDAO.getOne(2));
                    } else {
                        continue;
                    }
                }
            } else if (orderItem.getQuantity() <= orderItem.getProduct().getQuantity() && role) {
                count++;
                if (count == orderItems.size()) {
                    orderStages.add(orderStageDAO.getOne(5));
                }
            }
        }
        return orderStages;
    }
}
