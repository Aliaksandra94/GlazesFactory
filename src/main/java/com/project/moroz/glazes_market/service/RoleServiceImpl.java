package com.project.moroz.glazes_market.service;

import com.project.moroz.glazes_market.entity.Role;
import com.project.moroz.glazes_market.repository.RoleDAO;
import com.project.moroz.glazes_market.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private RoleDAO roleDAO;

    @Autowired
    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> returnAllRoles() {
        return roleDAO.findAll();
    }

    @Override
    public Role returnRoleByID(int id) {
        return roleDAO.getOne(id);
    }

    @Override
    public List<Role> returnAllRolesWithoutSeveralRoles(int roleID1, int roleID2) {
        return roleDAO.findRolesWithoutSeveralRoles(roleID1, roleID2);
    }
}
