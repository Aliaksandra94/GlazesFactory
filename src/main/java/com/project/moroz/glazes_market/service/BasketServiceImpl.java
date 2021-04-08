package com.project.moroz.glazes_market.service;

import com.project.moroz.glazes_market.entity.Basket;
import com.project.moroz.glazes_market.entity.BasketItem;
import com.project.moroz.glazes_market.entity.Product;
import com.project.moroz.glazes_market.entity.User;
import com.project.moroz.glazes_market.repository.BasketDAO;
import com.project.moroz.glazes_market.repository.BasketItemDAO;
import com.project.moroz.glazes_market.repository.ProductDAO;
import com.project.moroz.glazes_market.repository.UserDAO;
import com.project.moroz.glazes_market.service.interfaces.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl implements BasketService {
    private UserDAO userDAO;
    private BasketDAO basketDAO;
    private BasketItemDAO basketItemDAO;
    private ProductDAO productDAO;

    @Autowired
    public void setBasketDAO(BasketDAO basketDAO) {
        this.basketDAO = basketDAO;
    }

    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setBasketItemDAO(BasketItemDAO basketItemDAO) {
        this.basketItemDAO = basketItemDAO;
    }

    @Override
    public void addProductToBasket(int userId, int productId) {
        User user = userDAO.getOne(userId);
        try {
            if (user.getBasket().getBasketItems().stream().anyMatch(basketItem -> basketItem.getProducts().get(0).getId() == productId)) {
                BasketItem basketItem = user.getBasket().getBasketItems().stream().filter(s -> s.getProducts().get(0).getId() == productId).collect(Collectors.toList()).get(0);
                int firstCountInBasketItem = basketItem.getQuantity();
                basketItem.setQuantity(firstCountInBasketItem + 1);
                basketItemDAO.save(basketItem);
            } else {
                Product product = productDAO.getOne(productId);
                BasketItem basketItem = new BasketItem(user.getBasket(), 1);
                product.getBasketItems().add(basketItem);
                basketItemDAO.save(basketItem);
                productDAO.save(product);
            }
        } catch (NullPointerException e) {
            Product product = productDAO.getOne(productId);
            BasketItem basketItem = new BasketItem(user.getBasket(), 1);
            product.getBasketItems().add(basketItem);
            basketItemDAO.save(basketItem);
            productDAO.save(product);
        }
    }

    @Override
    public void addProductToBasketWithoutUser(int productId) {
 //       try {
                Product product = productDAO.getOne(productId);
                BasketItem basketItem = new BasketItem(getBasketOrCreateWithoutUser(), 1);
                product.getBasketItems().add(basketItem);
                basketItemDAO.save(basketItem);
                productDAO.save(product);
    }

    @Override
    public void updateQuantity(int basketItemId, int quantity) {
        BasketItem basketItem = basketItemDAO.getOne(basketItemId);
        basketItem.setQuantity(quantity);
        basketItemDAO.save(basketItem);
    }

    @Override
    public List<BasketItem> returnListOfProductsInBasket(int userId) {
        return basketDAO.returnListOfProductsInBasket(userId);
    }

    @Override
    public void deleteItem(int userId, int idProduct) {
        User user = userDAO.getOne(userId);
        BasketItem basketItem = user.getBasket().getBasketItems().stream().filter(s -> s.getProducts().get(0).getId() == idProduct).collect(Collectors.toList()).get(0);
        Product product = productDAO.getOne(idProduct);
        user.getBasket().getBasketItems().remove(basketItem);
        product.getBasketItems().remove(basketItem);
        basketItemDAO.delete(basketItem);
    }

    @Override
    public String returnTotalCostOfTheOrder(int userId) {
        User user = userDAO.getOne(userId);
        try {
            double totalCost = user.getBasket().getBasketItems().stream().mapToDouble(item -> item.getQuantity() * item.getProducts().get(0).getPrice()).sum();
            DecimalFormat format = new DecimalFormat("##.00");
            DecimalFormatSymbols dfs = format.getDecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            format.setDecimalFormatSymbols(dfs);
            if (user == null) {
                return format.format(totalCost);
            } else {
                if ((totalCost - (totalCost / 100 * user.getDiscount())) > 200) {
                    double newTotalCost = totalCost - (totalCost / 100 * user.getDiscount());
                    return format.format(newTotalCost - (newTotalCost / 100 * 10));
                } else {
                    return format.format(totalCost - (totalCost / 100 * user.getDiscount()));
                }
            }
        } catch (NullPointerException e) {
            return "0";
        }
    }

    @Override
    public void cleanBasket(int userId) {
        User user = userDAO.getOne(userId);
        while (!user.getBasket().getBasketItems().isEmpty()) {
            deleteItem(user.getId(), user.getBasket().getBasketItems().get(0).getProducts().get(0).getId());
        }
    }

    @Override
    public void submitProductInBasket(int userId, int productId) {
        User user = userDAO.getOne(userId);
        BasketItem basketItem = user.getBasket().getBasketItems().stream().filter(s -> s.getProducts().get(0).getId() == productId).collect(Collectors.toList()).get(0);
        int firstCountInBasketItem = basketItem.getQuantity();
        if (firstCountInBasketItem == 1) {
            deleteItem(userId, productId);
        } else {
            basketItem.setQuantity(firstCountInBasketItem - 1);
            basketItemDAO.save(basketItem);
        }
    }

    @Transactional
    @Override
    public Basket getBasketOrCreate(int userId) {
        User user = userDAO.getOne(userId);
        Basket basket = basketDAO.findByUserId(user.getId());
        return basket;
    }

    @Override
    public Basket getBasketOrCreateWithoutUser() {
        return basketDAO.save(new Basket());
    }

    private Basket createBasket(User user) {
        return basketDAO.save(new Basket(user));
    }
}


