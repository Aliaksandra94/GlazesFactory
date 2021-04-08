package com.project.moroz.glazes_market.service.interfaces;

import com.project.moroz.glazes_market.entity.Role;
import com.project.moroz.glazes_market.entity.Solvency;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> returnAllRoles();
    Role returnRoleByID(int id);
    List<Role> returnAllRolesWithoutSeveralRoles(int roleID1, int roleID2);
}
