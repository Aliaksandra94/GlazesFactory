package com.project.moroz.glazes_market.entity;

import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "basket")
@Scope("singleton")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "basket")
    private List<BasketItem> basketItems;

    public Basket() {
    }

    public Basket(int id, User user, List<BasketItem> basketItems) {
        this.id = id;
        this.user = user;
        this.basketItems = basketItems;
    }

    public Basket(User user) {
        this.user=user;
    }

    public boolean isEmpty() {
        return basketItems.isEmpty();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BasketItem> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(List<BasketItem> basketItems) {
        this.basketItems = basketItems;
    }
}

