package com.project.moroz.glazes_market.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "managers")
@DiscriminatorValue("Managers")
public class Manager implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 3, max = 15, message = "Name should be between 3 and 15 characters.")
    private String name;

    @Column(name = "login")
    @NotEmpty(message = "Login shouldn't be empty")
    @Size(min = 3, max = 15, message = "Login should be between 3 and 15 characters.")
    //@UniqueLoginManager
    private String login;

    @Column(name = "password")
    @NotEmpty(message = "Age shouldn't be empty")
    //@Size(min = 3, max = 10, message = "Pass should be between 3 and 15 characters.")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "manager_role",
            joinColumns = {
                    @JoinColumn(name = "manager_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "manager")
    private List<User> users;

    @Transient
    private final boolean isActive = true;

    public Manager() {
    }

    public Manager(
            int id, String name, String login, String password, Set<Role> roles,
            List<User> users) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.roles = roles;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
//        for (Role role : roles) {
//            list.add(new SimpleGrantedAuthority(role.getName()));
//        }
        roles.forEach(role -> list.add(new SimpleGrantedAuthority(role.getName())));
        return list;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromManager(Manager manager){
        return new org.springframework.security.core.userdetails.User(
                manager.getLogin(), manager.getPassword(),
                manager.isActive(),
                manager.isActive(),
                manager.isActive(),
                manager.isActive(),
                manager.getAuthorities()
        );
    }
}
