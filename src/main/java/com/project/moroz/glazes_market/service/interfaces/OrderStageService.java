package com.project.moroz.glazes_market.service.interfaces;

import com.project.moroz.glazes_market.entity.Order;
import com.project.moroz.glazes_market.entity.OrderStage;
import com.project.moroz.glazes_market.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderStageService {
    List<OrderStage> returnAllOrderStage();

    OrderStage returnOrderStageById(int orderStageId);

    List<Integer> returnAllOrderStageId();

    List<OrderStage> returnAvailableOrderStageId(Order order, boolean role, HttpServletRequest request);
}
