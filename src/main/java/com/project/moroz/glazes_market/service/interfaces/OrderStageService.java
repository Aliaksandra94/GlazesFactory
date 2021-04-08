package com.project.moroz.glazes_market.service.interfaces;

import com.project.moroz.glazes_market.entity.OrderStage;

import java.util.List;

public interface OrderStageService {
    List<OrderStage> returnAllOrderStage();

    OrderStage returnOrderStageById(int orderStageId);

    List<Integer> returnAllOrderStageId();
}
