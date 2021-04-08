package com.project.moroz.glazes_market.service;

import com.project.moroz.glazes_market.entity.*;
import com.project.moroz.glazes_market.form.UserForm;
import com.project.moroz.glazes_market.repository.*;
import com.project.moroz.glazes_market.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private ManagerDAO managerDAO;
    private SolvencyDAO solvencyDAO;
    private OrderDAO orderDAO;
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setbCryptPasswordEncoder(PasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Autowired
    public void setManagerDAO(ManagerDAO managerDAO) {
        this.managerDAO = managerDAO;
    }

    @Autowired
    public void setSolvencyDAO(SolvencyDAO solvencyDAO) {
        this.solvencyDAO = solvencyDAO;
    }

    @Autowired
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public List<User> returnAllUsers() {
        return userDAO.findAllByRoleID(4);
    }


    public List<User> returnAllUsersByManagerId(int managerId) {
        return userDAO.findAllByManagerId(managerId);
    }


    public User returnUserById(int userID) {
        return userDAO.getOne(userID);
    }


    public User returnUserByLogin(String login) {
        return userDAO.findByLogin(login);
    }


    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    @Override
    public void saveUserWithoutPasswordEncode(User user) {
        userDAO.save(user);
    }

    @Override
    public void deleteUser(User user) {
        user.getRoles().clear();
        user.setManager(null);
        user.setSolvency(null);
        userDAO.delete(user);
    }

    @Override
    public void setRoleManagerSolvency(int userId, int managerID, int solvencyID) {
        User user = userDAO.getOne(userId);
        Set<Role> roles = new HashSet<>();
        roles.add(roleDAO.getOne(4));
        user.setManager(managerDAO.getOne(managerID));
        user.setSolvency(solvencyDAO.getOne(solvencyID));
        user.setRoles(roles);
    }

    @Override
    public void updateUserWithNewData(int userId, String userName, String login, String password, double discount, int managerID, int solvencyID) {
        User user = userDAO.getOne(userId);
        Set<Role> roles = new HashSet<>();
        roles.add(roleDAO.getOne(4));
        user.setDiscount(discount);
        user.setLogin(login);
        user.setName(userName);
        if (user.getPassword().equals(password)) {
            user.setPassword(password);
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(password));
        }
        user.setManager(managerDAO.getOne(managerID));
        user.setSolvency(solvencyDAO.getOne(solvencyID));
        user.setRoles(roles);
        userDAO.save(user);
    }

    @Override
    public void updateUsersetEmail(int userId, String email) {
        User user = userDAO.getOne(userId);
        user.setEmail(email);
        userDAO.save(user);
    }

    // @Override
    public void updateUserWithNewDataWithoutPassEncoder(int userId, String userName, String login, String password, double discount, int managerID, int solvencyID) {
        User user = userDAO.getOne(userId);
        Set<Role> roles = new HashSet<>();
        roles.add(roleDAO.getOne(4));
        user.setDiscount(discount);
        user.setLogin(login);
        user.setName(userName);
        user.setPassword(password);
        user.setManager(managerDAO.getOne(managerID));
        user.setSolvency(solvencyDAO.getOne(solvencyID));
        user.setRoles(roles);
        userDAO.save(user);
    }

    @Override
    public void updateUserWithNewDataAndEmail(int userId, String userName, String login, String password, double discount, int managerID, int solvencyID, String email) {
        User user = userDAO.getOne(userId);
        Set<Role> roles = new HashSet<>();
        roles.add(roleDAO.getOne(4));
        user.setDiscount(discount);
        user.setLogin(login);
        user.setName(userName);
        if (email.equals("")) {
            user.setEmail(null);
        } else {
            user.setEmail(email);
        }
        if (user.getPassword().equals(password)) {
            user.setPassword(password);
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(password));
        }
        user.setManager(managerDAO.getOne(managerID));
        user.setSolvency(solvencyDAO.getOne(solvencyID));
        user.setRoles(roles);
        userDAO.save(user);
    }

    @Override
    public void registerNewUser(UserForm userForm, String login) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleDAO.getOne(4));
        User user = new User();
        user.setName(userForm.getName());
        user.setLogin(userForm.getLogin());
        user.setRoles(roles);
        user.setDiscount(2.3);
        user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        List<Manager> managers = managerDAO.findAllByRoleID(2);
        int randomID = new Random().nextInt(managers.size());
        user.setManager(managers.get(randomID));
        user.setSolvency(solvencyDAO.getOne(1));
        userDAO.save(user);
    }

    @Override
    public boolean isLoginAlreadyInUse(String login) {
        boolean userInDb = true;
        if (userDAO.findByLogin(login) == null) userInDb = false;
        return userInDb;
    }

    @Override
    public List<User> returnUsersByNameIgnoreCase(String name) {
        List<User> users = new ArrayList<>();
        String nameWithFirstCharacterToUpperCase = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        String nameToLowerCase = name.toLowerCase();
        String nameToUpperCase = name.toUpperCase();
        if (userDAO.findAllUserByName(nameToLowerCase) != null) {
            List<User> users1 = userDAO.findAllUserByName(nameToLowerCase);
            for (User user : users1) {
                users.add(user);
            }
        }
        if (userDAO.findAllUserByName(nameWithFirstCharacterToUpperCase) != null) {
            for (User user : userDAO.findAllUserByName(nameWithFirstCharacterToUpperCase)) {
                users.add(user);
            }
        }
        if (userDAO.findAllUserByName(nameToUpperCase) != null) {
            for (User user : userDAO.findAllUserByName(nameToUpperCase)) {
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public User returnUserByNameIgnoreCase(String name) {
        String nameWithFirstCharacterToUpperCase = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        String nameToLowerCase = name.toLowerCase();
        String nameToUpperCase = name.toUpperCase();
        User user = null;
        if (userDAO.findUserByName(name) != null) {
            user = userDAO.findUserByName(name);
        } else if (userDAO.findUserByName(name) == null && userDAO.findByLogin(nameToLowerCase) != null) {
            user = userDAO.findUserByName(nameToLowerCase);
        } else if (userDAO.findUserByName(nameWithFirstCharacterToUpperCase) != null) {
            user = userDAO.findUserByName(nameWithFirstCharacterToUpperCase);
        } else if (userDAO.findUserByName(nameToUpperCase) != null) {
            user = userDAO.findUserByName(nameToUpperCase);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDAO.findByLogin(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.fromUser(user);
    }

    @Override
    public boolean isUsernameUnique(String name) {
        String nameWithFirstCharacterToUpperCase = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        String nameToUpperCase = name.toUpperCase();
        String nameToLowerCase = name.toLowerCase();
        int count = 0;
        if (userDAO.findAllUserByName(name).size() != 0 || !userDAO.findAllUserByName(name).isEmpty()) {
            count++;
        }
        if (userDAO.findAllUserByName(nameToLowerCase).size() != 0 || !userDAO.findAllUserByName(nameToLowerCase).isEmpty()) {
            count++;
        }
        if (userDAO.findAllUserByName(nameWithFirstCharacterToUpperCase).size() != 0 || !userDAO.findAllUserByName(nameWithFirstCharacterToUpperCase).isEmpty()) {
            count++;
        }
        if (userDAO.findAllUserByName(nameToUpperCase).size() != 0 || !userDAO.findAllUserByName(nameToUpperCase).isEmpty()) {
            count++;
        }
        return (count == 1) ? true : false;
    }

    @Override
    public User returnUserByLoginIgnoreCase(String login) {
        String loginWithFirstCharacterToUpperCase = login.substring(0, 1).toUpperCase() + login.substring(1).toLowerCase();
        String loginToUpperCase = login.toUpperCase();
        String loginToLowerCase = login.toLowerCase();
        User user = null;
        if (userDAO.findByLogin(login) != null) {
            user = userDAO.findByLogin(login);
        } else if (userDAO.findByLogin(login) == null && userDAO.findByLogin(loginToLowerCase) != null) {
            user = userDAO.findByLogin(loginToLowerCase);
        } else if (userDAO.findByLogin(loginWithFirstCharacterToUpperCase) != null) {
            user = userDAO.findByLogin(loginWithFirstCharacterToUpperCase);
        } else if (userDAO.findByLogin(loginToUpperCase) != null) {
            user = userDAO.findByLogin(loginToUpperCase);
        }
        return user;
    }

    @Override
    public List<Order> returnUserOrders(String field) {
        List<Order> orders = new ArrayList<>();
        if (returnUserByLoginIgnoreCase(field) != null) {
            orders = orderDAO.findByUserId(returnUserByLoginIgnoreCase(field).getId());
        } else {
            if (isUsernameUnique(field)) {
                if (returnUserByNameIgnoreCase(field) != null) {
                    orders = orderDAO.findByUserId(returnUserByNameIgnoreCase(field).getId());
                }
            } else {
                if (returnUsersByNameIgnoreCase(field) != null) {
                    for (User user : returnUsersByNameIgnoreCase(field)) {
                        for (Order order : user.getOrders()) {
                            orders.add(order);
                        }
                    }
                }
            }
        }
        return orders;
    }

    @Override
    public List<Order> returnUserOrdersByNameOrLoginAndManagerId(String field, int managerId) {
        List<Order> orders = new ArrayList<>();
        if (returnUserByLoginIgnoreCase(field) != null && returnUserByLoginIgnoreCase(field).getManager().getId() == managerId) {
            orders = orderDAO.findByUserId(returnUserByLoginIgnoreCase(field).getId());
        } else {
            if (isUsernameUnique(field)) {
                if (returnUserByNameIgnoreCase(field) != null && returnUserByNameIgnoreCase(field).getManager().getId() == managerId) {
                    orders = orderDAO.findByUserId(returnUserByNameIgnoreCase(field).getId());
                }
            } else {
                if (returnUsersByNameIgnoreCase(field) != null) {
                    for (User user : returnUsersByNameIgnoreCase(field)) {
                        for (Order order : user.getOrders()) {
                            if (user.getManager().getId() == managerId) {
                                orders.add(order);
                            }
                        }
                    }
                }
            }
        }
        return orders;
    }

    @Override
    public List<User> returnUserOrUsersByLoginOrName(String field) {
        List<User> users = new ArrayList<>();
        if (returnUserByLoginIgnoreCase(field) != null) {
            users.add(returnUserByLoginIgnoreCase(field));
        } else {
            if (isUsernameUnique(field)) {
                if (returnUserByNameIgnoreCase(field) != null) {
                    users.add(returnUserByNameIgnoreCase(field));
                }
            } else {
                if (returnUsersByNameIgnoreCase(field) != null) {
                    for (User user : returnUsersByNameIgnoreCase(field)) {
                        users.add(user);
                    }
                }
            }
        }
        return users;
    }

    @Override
    public List<User> returnUserOrUsersByLoginOrNameAndManagerId(String field, int managerId) {
        List<User> users = new ArrayList<>();
        if (returnUserByLoginIgnoreCase(field) != null && returnUserByLoginIgnoreCase(field).getManager().getId() == managerId) {
            users.add(returnUserByLoginIgnoreCase(field));
        } else {
            if (isUsernameUnique(field)) {
                if (returnUserByNameIgnoreCase(field) != null && returnUserByNameIgnoreCase(field).getManager().getId() == managerId) {
                    users.add(returnUserByNameIgnoreCase(field));
                }
            } else {
                if (returnUsersByNameIgnoreCase(field) != null) {
                    for (User user : returnUsersByNameIgnoreCase(field)) {
                        if (user.getManager().getId() == managerId) {
                            users.add(user);
                        }
                    }
                }
            }
        }
        return users;
    }

    @Override
    public double getOrdersAmount(List<Order> orders) {
        double total = 0;
        for (Order order:orders){
            total += order.getAmount();
        }
        return total;
    }
}
