package com.project.moroz.glazes_market.repository;

import com.project.moroz.glazes_market.entity.Manager;
import com.project.moroz.glazes_market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerDAO extends JpaRepository<Manager, Integer> {
    Manager findByLoginAndPassword(String login, String password);

    Manager findByLogin(String Login);
    Manager findByName(String name);

    @Query(value = "select role.managers from Role role where role.id =:id")
    List<Manager> findAllByRoleID(int id);

    void deleteUserById(int id);
    @Query(value = "select role.managers from Role role where not role.id = 1")
    List<Manager> findAllManagersWithoutAdmin();

    @Query(value = "select sum (o.amount) from Order o where o.orderStage.orderStageID =:orderStageId and o.user.manager.id =:managerId")
    Double findSumOfOrdersBySellerIdAndOrderStageId(int managerId, int orderStageId);

    @Query(value = "select count (o) from Order o where o.orderStage.orderStageID =:orderStageId and o.user.manager.id =:managerId")
    int countOrdersBySellerIdAndOrderStageId(int managerId, int orderStageId);

    @Query(value = "select sum (o.amount) from Order o where o.orderStage.orderStageID =:orderStageId")
    Double findSumOfOrdersByOrderStageId(int orderStageId);

    @Query(value = "select count (o) from Order o where o.orderStage.orderStageID =:orderStageId")
    int countOrdersByOrderStageId(int orderStageId);
}