package com.project.moroz.glazes_market.form;

import com.project.moroz.glazes_market.entity.annotation.UniqueLogin;
import com.project.moroz.glazes_market.model.User;

public class UserForm {
    private String name;
    @UniqueLogin
    private String login;
    private String password;
    private boolean valid;

    public UserForm() {

    }

    public UserForm(User userInfo) {
        if (userInfo != null) {
            this.name = userInfo.getName();
            this.login = userInfo.getLogin();
            this.password = userInfo.getPassword();
        }
    }

    public UserForm(com.project.moroz.glazes_market.entity.User userInfo) {
        if (userInfo != null) {
            this.name = userInfo.getName();
            this.login = userInfo.getLogin();
            this.password = userInfo.getPassword();
        }
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
}
