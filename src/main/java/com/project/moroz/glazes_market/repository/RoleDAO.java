package com.project.moroz.glazes_market.repository;

import com.project.moroz.glazes_market.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDAO extends JpaRepository<Role, Integer> {
    @Query(value = "from Role role where not role.id =:roleId1 and not role.id =:roleId2")
    List<Role> findRolesWithoutSeveralRoles(int roleId1, int roleId2);
}
