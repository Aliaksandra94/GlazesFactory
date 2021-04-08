package com.project.moroz.glazes_market.entity;

import com.project.moroz.glazes_market.entity.annotation.UniqueLogin;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.logging.java.JavaLoggingSystem;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 3, max = 15, message = "Name should be between 3 and 15 characters.")
    private String name;

    @Column(name = "login", unique = true)
    @NotEmpty(message = "Login shouldn't be empty")
    @Size(min = 3, max = 15, message = "Login should be between 3 and 15 characters.")
    //@UniqueLogin
    private String login;

    @Column(name = "password")
    @NotEmpty(message = "Age shouldn't be empty")
    //@Size(min = 3, max = 10, message = "Pass should be between 3 and 15 characters.")
    private String password;

    @Column(name = "discount")
    @Min(value = 1, message = "Discount should be more or equal 1%")
    @Max(value = 25, message = "Discount should be less or equal 25%")
    private double discount;

    @ManyToOne
    @JoinColumn(name = "solvency_id")
    private Solvency solvency;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Basket basket;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "user_role",
            joinColumns = {
                    @JoinColumn(name = "user_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @Transient
    private final boolean isActive = true;
    @Email
    @Column(name = "email")
    private String email;

    public User() {
    }

    public User(
            int id, String name, String login, String password,
            double discount,
            Solvency solvency, Basket basket, List<Order> orders, Manager manager, Set<Role> roles,
            String email) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.discount = discount;
        this.solvency = solvency;
        this.basket = basket;
        this.orders = orders;
        this.manager = manager;
        this.roles = roles;
        this.email=email;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Solvency getSolvency() {
        return solvency;
    }

    public void setSolvency(Solvency solvency) {
        this.solvency = solvency;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority(role.getName()));
        }
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

    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPassword(),
                user.isActive(),
                user.isActive(),
                user.isActive(),
                user.isActive(),
                user.getAuthorities()
        );
    }

    public void setBasket(com.project.moroz.glazes_market.model.Basket basketInSession) {
    }
}