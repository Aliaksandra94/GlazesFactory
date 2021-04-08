package com.project.moroz.glazes_market.service;

import com.project.moroz.glazes_market.entity.Manager;
import com.project.moroz.glazes_market.entity.Role;
import com.project.moroz.glazes_market.entity.User;
import com.project.moroz.glazes_market.form.UserForm;
import com.project.moroz.glazes_market.repository.ManagerDAO;
import com.project.moroz.glazes_market.repository.RoleDAO;
import com.project.moroz.glazes_market.repository.UserDAO;
import com.project.moroz.glazes_market.service.interfaces.ManagerService;
import com.project.moroz.glazes_market.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {
    private ManagerDAO managerDAO;
    private RoleDAO roleDAO;
    private PasswordEncoder bCryptPasswordEncoder;
    private UserDAO userDAO;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setbCryptPasswordEncoder(PasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setManagerDAO(ManagerDAO managerDAO) {
        this.managerDAO = managerDAO;
    }

    @Autowired
    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<Manager> returnAllManagers() {
        return managerDAO.findAll();
    }

    @Override
    public Manager returnManagerById(int managerID) {
        return managerDAO.getOne(managerID);
    }

    @Override
    public List<User> returnAllUsersByManagerId(int managerId) {
        return userDAO.findAllByManagerId(managerId);
    }

    @Override
    public Manager returnManagerByLogin(String login) {
        return managerDAO.findByLogin(login);
    }

    @Override
    public Manager returnManagerByName(String name) {
        return managerDAO.findByName(name);
    }


    @Override
    public void saveManager(Manager manager) {
        manager.setPassword(bCryptPasswordEncoder.encode(manager.getPassword()));
        managerDAO.save(manager);
        User user = new User();
        user.setName(manager.getName());
        user.setLogin(manager.getLogin());
        user.setPassword(manager.getPassword());
        Set<Role> managerRoles = manager.getRoles();
        Set<Role> roles = new HashSet<>();
        for (Role role : managerRoles) {
            roles.add(role);
        }
        user.setRoles(roles);
        user.setDiscount(1);
        userService.saveUserWithoutPasswordEncode(user);
    }

    @Override
    public void saveManagerWithoutPasswordEncode(Manager manager) {
        managerDAO.save(manager);
    }

    @Override
    public void deleteManager(Manager manager) {
        User user = userDAO.findByLogin(manager.getLogin());
        manager = managerDAO.findByLogin(user.getLogin());
        user.getRoles().clear();
        manager.setRoles(null);
        List<User> users = new ArrayList<>();
        for (User userAdd : manager.getUsers()) {
            userAdd.setManager(null);
            users.add(userAdd);
        }
        manager.getUsers().removeAll(users);
        userDAO.delete(user);
        managerDAO.delete(manager);
        List<Manager> managers = managerDAO.findAllByRoleID(2);
        if (users.size() > 0) {
            for (User userToSetNewManager : users) {
                int random = new Random().nextInt(managers.size());
                userToSetNewManager.setManager(managers.get(random));
                userDAO.save(userToSetNewManager);
            }
        }
    }

    @Override
    public void setRole(int managerID, int roleID) {
        Manager manager = managerDAO.getOne(managerID);
        Set<Role> roles = new HashSet<>();
        roles.add(roleDAO.getOne(roleID));
        manager.setRoles(roles);
    }

    @Override
    public List<Manager> returnManagersByRoleId(int roleId) {
        return managerDAO.findAllByRoleID(roleId);
    }

    @Override
    public void updateManagerWithNewData(int managerId, String managerName, String login, String password, List<User> users) {
        Manager manager = managerDAO.getOne(managerId);
        User user = userDAO.findByLogin(manager.getLogin());
        manager.setName(managerName);
        manager.setLogin(login);
        if (manager.getPassword().equals(password)) {
            manager.setPassword(password);
        } else {
            manager.setPassword(bCryptPasswordEncoder.encode(password));
        }
        managerDAO.save(manager);
        user.setName(manager.getName());
        user.setLogin(manager.getLogin());
        user.setPassword(manager.getPassword());
        userDAO.save(user);
    }

    @Override
    public boolean isLoginAlreadyInUse(String login) {
        boolean userInDb = true;
        if (managerDAO.findByLogin(login) == null) userInDb = false;
        return userInDb;
    }

    @Override
    public List<Manager> returnAllManagersWithoutAdmin() {
        return managerDAO.findAllManagersWithoutAdmin();
    }

    @Override
    public Map<Manager, List<Double>> returnManagersOrdersSumAndCount(List<Manager> managers) {
        Map<Manager, List<Double>> managerListMap = new HashMap<>();
        for (Manager manager : managers) {
            List<Double> orders = new ArrayList<>();
            double sumOfOrdersInProgress = 0;
            double countOrdersInProgress = 0;
            double sumOfOrdersReady = 0;
            double countOrdersReady = 0;
            for (Role role : manager.getRoles()) {
                if (role.getId() == 2) {
                    if (managerDAO.findSumOfOrdersBySellerIdAndOrderStageId(manager.getId(), 4) == null && managerDAO.findSumOfOrdersBySellerIdAndOrderStageId(manager.getId(), 5) != null) {
                        sumOfOrdersInProgress += managerDAO.findSumOfOrdersBySellerIdAndOrderStageId(manager.getId(), 5);
                        countOrdersInProgress += managerDAO.countOrdersBySellerIdAndOrderStageId(manager.getId(), 5);
                    } else if (managerDAO.findSumOfOrdersBySellerIdAndOrderStageId(manager.getId(), 4) != null && managerDAO.findSumOfOrdersBySellerIdAndOrderStageId(manager.getId(), 5) == null) {
                        sumOfOrdersInProgress += managerDAO.findSumOfOrdersBySellerIdAndOrderStageId(manager.getId(), 4);
                        countOrdersInProgress += managerDAO.countOrdersBySellerIdAndOrderStageId(manager.getId(), 4);
                    } else if (managerDAO.findSumOfOrdersBySellerIdAndOrderStageId(manager.getId(), 4) == null && managerDAO.findSumOfOrdersBySellerIdAndOrderStageId(manager.getId(), 5) == null) {
                        sumOfOrdersInProgress += 0;
                        countOrdersInProgress += 0;
                    } else {
                        sumOfOrdersInProgress += managerDAO.findSumOfOrdersBySellerIdAndOrderStageId(manager.getId(), 4) + managerDAO.findSumOfOrdersBySellerIdAndOrderStageId(manager.getId(), 5);
                        countOrdersInProgress += managerDAO.countOrdersBySellerIdAndOrderStageId(manager.getId(), 4) + managerDAO.countOrdersBySellerIdAndOrderStageId(manager.getId(), 5);
                    }
                    if (managerDAO.findSumOfOrdersBySellerIdAndOrderStageId(manager.getId(), 1) == null) {
                        sumOfOrdersReady += 0;
                        countOrdersReady += 0;
                    } else {
                        sumOfOrdersReady += managerDAO.findSumOfOrdersBySellerIdAndOrderStageId(manager.getId(), 1);
                        countOrdersReady += managerDAO.countOrdersBySellerIdAndOrderStageId(manager.getId(), 1);
                    }
                }
                if (role.getId() == 3) {
                    if (managerDAO.findSumOfOrdersByOrderStageId(6) == null) {
                        sumOfOrdersInProgress += 0;
                        countOrdersInProgress += 0;
                    } else {
                        sumOfOrdersInProgress += managerDAO.findSumOfOrdersByOrderStageId(6);
                        countOrdersInProgress += managerDAO.countOrdersByOrderStageId(6);
                    }
                    if (managerDAO.findSumOfOrdersByOrderStageId(7) == null) {
                        sumOfOrdersReady += 0;
                        countOrdersReady += 0;
                    } else {
                        sumOfOrdersReady += managerDAO.findSumOfOrdersByOrderStageId(7);
                        countOrdersReady += managerDAO.countOrdersByOrderStageId(7);
                    }

                }
                if (role.getId() == 5) {
                    if (managerDAO.findSumOfOrdersByOrderStageId(2) == null && managerDAO.findSumOfOrdersByOrderStageId(3) != null) {
                        sumOfOrdersInProgress += managerDAO.findSumOfOrdersByOrderStageId(3);
                        countOrdersInProgress += managerDAO.countOrdersByOrderStageId(3);
                    } else if (managerDAO.findSumOfOrdersByOrderStageId(2) != null && managerDAO.findSumOfOrdersByOrderStageId(3) == null) {
                        sumOfOrdersInProgress += managerDAO.findSumOfOrdersByOrderStageId(2);
                        countOrdersInProgress += managerDAO.countOrdersByOrderStageId(2);
                    } else if (managerDAO.findSumOfOrdersByOrderStageId(2) == null && managerDAO.findSumOfOrdersByOrderStageId(3) == null) {
                        sumOfOrdersInProgress += 0;
                        countOrdersInProgress += 0;
                    } else {
                        sumOfOrdersInProgress += managerDAO.findSumOfOrdersByOrderStageId(2) + managerDAO.findSumOfOrdersByOrderStageId(3);
                        countOrdersInProgress += managerDAO.countOrdersByOrderStageId(2) + managerDAO.countOrdersByOrderStageId(3);
                    }
                    if (managerDAO.findSumOfOrdersByOrderStageId(5) == null) {
                        sumOfOrdersReady += 0;
                        countOrdersReady += 0;
                    } else {
                        sumOfOrdersReady += managerDAO.findSumOfOrdersByOrderStageId(5);
                        countOrdersReady += managerDAO.countOrdersByOrderStageId(5);
                    }
                }
            }
            orders.add(sumOfOrdersInProgress);
            orders.add(countOrdersInProgress);
            orders.add(sumOfOrdersReady);
            orders.add(countOrdersReady);
            managerListMap.put(manager, orders);
        }
        return managerListMap;
    }

    @Override
    public List<Manager> returnManagersByUsers(List<User> users) {
        List<Manager> managers = new ArrayList<>();
        for (User user : users) {
            if (returnManagerByLogin(user.getLogin()) == null) {
                continue;
            } else{
            managers.add(returnManagerByLogin(user.getLogin()));
            }
        }
        return managers;
    }
}
