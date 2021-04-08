package com.project.moroz.glazes_market.service;

import com.project.moroz.glazes_market.entity.BasketItem;
import com.project.moroz.glazes_market.entity.User;
import com.project.moroz.glazes_market.repository.BasketDAO;
import com.project.moroz.glazes_market.repository.BasketItemDAO;
import com.project.moroz.glazes_market.repository.ProductDAO;
import com.project.moroz.glazes_market.repository.UserDAO;
import com.project.moroz.glazes_market.service.interfaces.BasketItemService;
import com.project.moroz.glazes_market.service.interfaces.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BasketItemServiceImpl implements BasketItemService {
    private UserDAO userDAO;
    private BasketDAO basketDAO;
    private BasketItemDAO basketItemDAO;
    private ProductDAO productDAO;

    @Autowired
    public void setBasketDAO(BasketDAO basketDAO) {
        this.basketDAO = basketDAO;
    }

    @Autowired
    public void setBasketItemDAO(BasketItemDAO basketItemDAO) {
        this.basketItemDAO = basketItemDAO;
    }

    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public BasketItem returnBasketItemById(int id) {
        return basketItemDAO.getOne(id);
    }

    @Override
    public void deleteBasketItem(BasketItem basketItem) {
        basketItemDAO.delete(basketItem);
    }
}
