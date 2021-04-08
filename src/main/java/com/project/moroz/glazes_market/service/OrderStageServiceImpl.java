package com.project.moroz.glazes_market.service;

import com.project.moroz.glazes_market.entity.OrderStage;
import com.project.moroz.glazes_market.repository.OrderStageDAO;
import com.project.moroz.glazes_market.service.interfaces.OrderStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderStageServiceImpl implements OrderStageService {
    private OrderStageDAO orderStageDAO;

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
}
