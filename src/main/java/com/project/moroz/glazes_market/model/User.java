package com.project.moroz.glazes_market.model;

import com.project.moroz.glazes_market.entity.Manager;
import com.project.moroz.glazes_market.entity.Role;
import com.project.moroz.glazes_market.entity.Solvency;
import com.project.moroz.glazes_market.form.UserForm;

import java.util.HashSet;
import java.util.Set;

public class User {
    private int id;
    private double discount;
    private String name;
    private String login;
    private String password;
    private Set<Role> roles = new HashSet<Role>();
    private Manager manager;
    private Solvency solvency;

    private boolean valid;

    public User() {

    }

    public User(double discount, String name, String login, String password, Set<Role> roles, Manager manager, Solvency solvency) {
        this.discount = discount;
        this.name = name;
        this.login = login;
        this.password = password;
        this.roles = roles;
        this.manager = manager;
        this.solvency = solvency;
    }

    public User(UserForm userForm) {
        this.name = userForm.getName();
        this.login = userForm.getLogin();
        this.password = userForm.getPassword();
        this.valid = userForm.isValid();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Solvency getSolvency() {
        return solvency;
    }

    public void setSolvency(Solvency solvency) {
        this.solvency = solvency;
    }
}
