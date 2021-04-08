package com.project.moroz.glazes_market.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "solvency")
public class Solvency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "solvency", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<User> users;

    public Solvency() {
    }

    public Solvency(int id, String name, List<User> users) {
        this.id = id;
        this.name = name;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Solvency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
