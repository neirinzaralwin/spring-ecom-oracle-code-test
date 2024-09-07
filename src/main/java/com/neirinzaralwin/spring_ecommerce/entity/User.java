package com.neirinzaralwin.spring_ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "USERS")
public class User {


    @Id
    @GeneratedValue
    private int userId;

    @Column(name="name")
    private String name;

    @Column(name="email", nullable=false, length=200)
    private String email;

    @Column(name="password", nullable=false, length=50)
    private String password;

    public int getId() {
        return userId;
    }

    public void setId(int id) {
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}