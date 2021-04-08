package com.project.moroz.glazes_market.service.interfaces;

import com.project.moroz.glazes_market.entity.Manager;
import com.project.moroz.glazes_market.entity.User;
import com.project.moroz.glazes_market.form.UserForm;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ManagerService {
    List<Manager> returnAllManagers();

    Manager returnManagerById(int managerID);

    List<Manager> returnAllManagersWithoutAdmin();

    List<User> returnAllUsersByManagerId(int ManagerId);
    Manager returnManagerByLogin(String login);
    Manager returnManagerByName(String name);
    void saveManager(Manager manager);
    void saveManagerWithoutPasswordEncode(Manager manager);

    void deleteManager(Manager manager);

    void setRole(int managerID, int roleID);

    List<Manager> returnManagersByRoleId(int roleId);

    void updateManagerWithNewData(int managerId, String managerName, String login,
                               String password, List<User> users);

    boolean isLoginAlreadyInUse(String login);

    Map<Manager, List<Double>> returnManagersOrdersSumAndCount(List<Manager> managers);

    List<Manager> returnManagersByUsers(List<User> users);

}