package com.receipt_app.models;

public class User {
    private int id;
    private String username;
    private String password;
    private String token;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String token) {
        this.username = username;
        this.token = token;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
