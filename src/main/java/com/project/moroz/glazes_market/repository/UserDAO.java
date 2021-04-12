package com.project.moroz.glazes_market.repository;

import com.project.moroz.glazes_market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
    User findByLogin(String Login);

    List<User> findAllByManagerId(int managerId);

    @Query(value = "select role.users from Role role where role.id =:id")
    List<User> findAllByRoleID(int id);

    List<User> findAllUserByName(String name);

    User findUserByName(String name);
    @Transactional
    @Modifying
    @Query(value = "delete from User u where u.id =:id")
    void deleteUserById(int id);
}
