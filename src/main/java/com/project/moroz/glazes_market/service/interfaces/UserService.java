package com.project.moroz.glazes_market.service.interfaces;

import com.project.moroz.glazes_market.entity.Order;
import com.project.moroz.glazes_market.entity.Product;
import com.project.moroz.glazes_market.entity.User;
import com.project.moroz.glazes_market.form.UserForm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> returnAllUsers();

    List<User> returnAllUsersByManagerId(int ManagerId);

    User returnUserById(int userID);

    User returnUserByLogin(String login);

    void saveUser(User user);

    void saveUserWithoutPasswordEncode(User user);

    void deleteUser(int id);

    void setRoleManagerSolvency(int userId, int managerID,
                                int solvencyID);

    void updateUserWithNewData(int userId, String userName, String login,
                               String password, double discount, int managerID,
                               int solvencyID);

    void updateUsersetEmail(int userId, String email);

    void updateUserWithNewDataWithoutPassEncoder(int userId, String userName, String login,
                                                 String password, double discount, int managerID,
                                                 int solvencyID);

    void updateUserWithNewDataAndEmail(int userId, String userName, String login,
                                       String password, double discount, int managerID,
                                       int solvencyID, String email);

    void registerNewUser(UserForm userForm, String login);

    boolean isLoginAlreadyInUse(String login);

    List<User> returnUsersByNameIgnoreCase(String name);

    User returnUserByNameIgnoreCase(String name);

    boolean isUsernameUnique(String name);

    User returnUserByLoginIgnoreCase(String login);

    List<Order> returnUserOrders(String field);

    List<User> returnUserOrUsersByLoginOrName(String field);

    List<User> returnUserOrUsersByLoginOrNameAndManagerId(String field, int managerId);

    List<Order> returnUserOrdersByNameOrLoginAndManagerId(String field, int managerId);

    double getOrdersAmount(List<Order> orders);

}