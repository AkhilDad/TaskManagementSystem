package com.upgrad.tms.entities;

import java.io.Serializable;

public class User implements Serializable {
    //incremental user id given to the user
    private long id;
    //the name of the user
    private String name;
    //the username of the user, used to uniquely identify the user
    private String username;
    //the password of the user, this is needed for the login
    private String password;
    //the role of the user, this would be assigned while creating a user.
    private UserRole userRole;

    public User(int id, String name, String username, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.userRole = role;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
