package com.sample.servlet.infrastructure.models;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String pass;

    @Column(name = "email")
    private String email;

    public User() {

    }

    public User(String login, String pass, String email) {
        this.login = login;
        this.pass = pass;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.pass = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
